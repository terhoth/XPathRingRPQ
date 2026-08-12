package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class XPathRpqListener extends xpathRpqBaseListener {

	private StringBuilder query = new StringBuilder();
	private Stack<StringBuilder> queryStack = new Stack<StringBuilder>();

	private Stack<String> axis = new Stack<String>();
	private Stack<String> startEdge = new Stack<String>();
	private Stack<String> endEdge = new Stack<String>();

	private String nCName;

	private boolean firstStep = true;
	private boolean deletedStep = false;

	private boolean insideAttributeTest = false;
	private Object attributeValue;

	private final boolean verbose = false;

	private Output output = new Output();

	public void setQuery(Object s) {
		this.query.append(s);
	}

	public Object getQuery() {
		return this.query;
	}

	@Override
	public void enterMain(xpathRpqParser.MainContext ctx) {
		this.queryStack.push(query);	
	}

	@Override
	public void exitMain(xpathRpqParser.MainContext ctx) {
		System.out.println("Ring query");
		this.query = this.queryStack.pop();
		System.out.println(this.query);
		System.out.println();
		System.out.println("Translation done");
	}

	@Override
	public void exitOrExpr(xpathRpqParser.OrExprContext ctx) {
		if (ctx.getChildCount() > 1) {
			String warnMessage = "Logical OR has not been implemented";
			output.printWarning(warnMessage);
		}
	}

	@Override 
	public void enterEqualityExpr(xpathRpqParser.EqualityExprContext ctx) {
		if (ctx.parent.getChildCount() > 1) {
			// separate and-combined expressions into different brackets
			// e.g. [@attr1 = 1 and @attr2 = 2] --> [@attr1 = 1][@attr2 = 2]
			if (this.verbose) System.out.println("enterEqualityExpr");
			this.queryStack.peek().append("][");
			this.insideAttributeTest = false;
			if (this.verbose) System.out.println(this.queryStack.peek());
		}
	}

	@Override 
	public void exitEqualityExpr(xpathRpqParser.EqualityExprContext ctx) {
		if (ctx.getChildCount() > 1) {
			if (this.verbose) System.out.println("exitEqualityExpr");
			this.queryStack.peek().append(" " + ctx.getChild(1) + " "); // operator
			this.queryStack.peek().append(this.attributeValue.toString());
			if (this.verbose) System.out.println(this.queryStack.peek());

			if (!insideAttributeTest) {
				String warnMessage = "Equality expressions may only be used to test attributes";
				output.printWarning(warnMessage);
			}
		}
		if (ctx.getChildCount() > 3) {
			String warnMessage = "Too many operands in equality expression";
			output.printWarning(warnMessage);
		}
	}

	@Override 
	public void exitRelationalExpr(xpathRpqParser.RelationalExprContext ctx) {
		if (ctx.getChildCount() > 1) {
			if (this.verbose) System.out.println("exitRelationalExpr");
			this.queryStack.peek().append(" " + ctx.getChild(1) + " "); // operator
			this.queryStack.peek().append(this.attributeValue.toString());
			if (this.verbose) System.out.println(this.queryStack.peek());
	
			if (!insideAttributeTest) {
				String warnMessage = "Relational expressions should begin with an @-prefixed attribute name";
				output.printWarning(warnMessage);
			}
		}
		if (ctx.getChildCount() > 3) {
			String warnMessage = "Too many operands in relational expression";
			output.printWarning(warnMessage);
		}
	}

	@Override
	public void exitAdditiveExpr(xpathRpqParser.AdditiveExprContext ctx) {
		if (ctx.getChildCount() > 1) {
			String warnMessage = "Addition has not been implemented";
			output.printWarning(warnMessage);
		}
	}

	@Override
	public void exitMultiplicativeExpr(xpathRpqParser.MultiplicativeExprContext ctx) {
		if (ctx.getChildCount() > 1) {
			String warnMessage = "Multiplication has not been implemented";
			output.printWarning(warnMessage);
		}
	}

	@Override
	public void exitUnionExprNoRoot(xpathRpqParser.UnionExprNoRootContext ctx) {
		if (ctx.getChildCount() > 1) {
			String warnMessage = "Union has not been implemented";
			output.printWarning(warnMessage);
		}
	}

	@Override
	public void exitPrimaryExpr(xpathRpqParser.PrimaryExprContext ctx) {
		if (this.verbose) System.out.println("exitPrimaryExpr");
		//Checking if the child node is leaf node
		if (ctx.getChild(0).getChild(0) == null) {
			this.attributeValue = ctx.getChild(0);
		} else {
			String warnMessage = "Functions, variables and parenthesized expressions are unimplemented";
			output.printWarning(warnMessage);
		}	
		if (this.verbose) System.out.println(this.queryStack.peek());
	}

	@Override
	public void enterRelativeLocationPath(xpathRpqParser.RelativeLocationPathContext ctx) {
		this.firstStep = true;
	}

	@Override
	public void exitRelativeLocationPath(xpathRpqParser.RelativeLocationPathContext ctx) {
		if (this.verbose) System.out.println("exitRelativeLocationPath");
		if (this.queryStack.size() == 1) {
			// this is the end of the main query
			int n_of_steps = (ctx.getChildCount() + 1) / 2;
			if (n_of_steps <= 1) {
				String warnMessage = "The query must contain at least one edge!";
				output.printWarning(warnMessage);
			} else if (n_of_steps % 2 == 0 || this.deletedStep) {
				// no vertex endpoint specified
				this.queryStack.peek().append(" ?y");
			} else {
				// put a space before the last vertex, and remove &
				int i = this.queryStack.peek().lastIndexOf("/<&");
				this.queryStack.peek().delete(i, i+3);
				this.queryStack.peek().insert(i, " <");
			}
		}
		if (this.verbose) System.out.println(this.queryStack.peek());
	}

	@Override
	public void exitAxisSpecifier(xpathRpqParser.AxisSpecifierContext ctx) {
		if (this.verbose) System.out.println("exitAxisSpecifier");
		StringBuilder sb = new StringBuilder();
		sb.append(ctx.getChild(0));

		if (sb.toString().equals("parent") || sb.toString().equals("reverse")) {
			this.axis.push("parent");
			this.startEdge.push("<%");
			this.endEdge.push(">");
		} else if (sb.toString().equals("ancestor") || sb.toString().equals("reverse-transitive")) {
			this.startEdge.push("(<%");
			this.endEdge.push(">)+");
			this.axis.push("ancestor");
		} else if (sb.toString().equals("child")) {
			this.startEdge.push("<");
			this.endEdge.push(">");
			this.axis.push("child");
		} else if (sb.toString().equals("descendant") || sb.toString().equals("transitive")) {
			this.startEdge.push("(<");
			this.endEdge.push(">)+");
			this.axis.push("descendant");
		} else if (sb.toString().equals("ancestor-or-self") || sb.toString().equals("reverse-transitive-or-self")) {
			this.startEdge.push("(<%");
			this.endEdge.push(">)*");
			this.axis.push("ancestor-or-self");
		} else if (sb.toString().equals("descendant-or-self") || sb.toString().equals("transitive-or-self")) {
			this.startEdge.push("(<");
			this.endEdge.push(">)*");
			this.axis.push("descendant-or-self");
		} else if (sb.toString().equals("attribute") || sb.toString().equals("@")) {
			this.startEdge.push("");
			this.endEdge.push("");
			this.axis.push("attribute");
			this.insideAttributeTest = true;
		} else if (sb.toString() == null || !sb.toString().isEmpty()) {
			this.startEdge.push("<");
			this.endEdge.push(">");
			this.axis.push("child");
		} else {
			throw new IllegalArgumentException("Unknown axis " + sb.toString());
		}

		this.queryStack.peek().append(this.startEdge.peek());
		if (this.verbose) System.out.println(this.queryStack.peek());
	}

	@Override
	public void exitAbbreviatedStep(xpathRpqParser.AbbreviatedStepContext ctx) {
		String warnMessage = "Abbreviated steps have not been implemented";
		output.printWarning(warnMessage);
	}

	@Override
	public void enterStep(xpathRpqParser.StepContext ctx) {
		if (this.insideAttributeTest) {
			String warnMessage = "Attribute tests must consist of an attribute name, operator and value.";
			output.printWarning(warnMessage);
		}
		if (!this.firstStep && this.queryStack.peek().charAt(this.queryStack.peek().length()-1) != ' ') {
			if (this.verbose) System.out.println("enterStep");
			this.queryStack.peek().append("/");
			if (this.verbose) System.out.println(this.queryStack.peek());
		}
		this.deletedStep = false;
	}

	@Override
	public void exitStep(xpathRpqParser.StepContext ctx) {
		if (this.verbose) System.out.println("exitStep");
		this.queryStack.peek().append(this.endEdge.peek());
	
		if (firstStep && this.queryStack.size() == 1) {
			// this is the very first vertex of the main query
			if (this.axis.peek() != "child")  {
				String warnMessage = "The first step should contain only a vertex name (or wildcard vertex)";
				output.printWarning(warnMessage);
			}
			if (this.queryStack.peek().toString().equals("<&>")) {
				// replace unspecified start vertex with variable
				this.queryStack.pop();
				this.queryStack.push(new StringBuilder("?x"));
			} else {
				// remove & from constant start vertex
				this.queryStack.peek().deleteCharAt(1); 
			}
			this.queryStack.peek().append(" ");

		} else {
			// remove the step if it was just a nameless vertex
			String uselessStep = "/" + this.startEdge.peek() + "&" + this.endEdge.peek();
			int i = this.queryStack.peek().indexOf(uselessStep);
			if (i != -1) {
				this.queryStack.peek().delete(i, this.queryStack.peek().length());
				this.deletedStep = true;
			}
			// add underscore to nameless edges (also remove needless reversion)
			i = this.queryStack.peek().indexOf("<>");
			if (i != -1) {
				this.queryStack.peek().replace(i, i+2, "<_>");
			}
			i = this.queryStack.peek().indexOf("<%>"); 
			if (i != -1) {
				this.queryStack.peek().replace(i, i+3, "<_>");
			}
		}

		this.axis.pop();
		this.startEdge.pop();
		this.endEdge.pop();
		this.firstStep = false;
		if (this.verbose) System.out.println(this.queryStack.peek());
	}

	@Override
	public void enterNameTest(xpathRpqParser.NameTestContext ctx) {
		if (this.verbose) System.out.println("enterNameTest");
		this.nCName = "";
		if (this.verbose) System.out.println(this.queryStack.peek());
	}

	@Override
	public void exitNameTest(xpathRpqParser.NameTestContext ctx) {
		if (this.verbose) System.out.println("exitNameTest");
		int n_of_steps = (ctx.parent.parent.parent.getChildCount() + 1) / 2;
		// main query begins with a vertex; subqueries (=predicates) begin with an edge
		if ((this.queryStack.size() == 1 && n_of_steps % 2 == 1) ||
			(this.queryStack.size() >  1 && n_of_steps % 2 == 0)) {
			this.queryStack.peek().append('&');
		}
		this.queryStack.peek().append(this.nCName);
		if (this.verbose) System.out.println(this.queryStack.peek());
	}

	@Override
	public void exitNCName(xpathRpqParser.NCNameContext ctx) {
		if (this.verbose) System.out.println("exitNameTest");
		StringBuilder sb = new StringBuilder();
		this.nCName = sb.append(ctx.getChild(0)).toString();	
		if (this.verbose) System.out.println(this.queryStack.peek());
	}

	@Override
	public void enterPredicate(xpathRpqParser.PredicateContext ctx) {
		if (this.verbose) System.out.println("enterPredicate");
		if (this.firstStep && this.queryStack.size() == 1) {
			String warnMessage = "The first step should contain only a vertex name (or wildcard vertex)";
			output.printWarning(warnMessage);
		}	
		int n_of_steps = (ctx.parent.parent.getChildCount() + 1) / 2;
		if (this.insideAttributeTest) {
			String warnMessage = "Attribute tests may not contain predicates";
			output.printWarning(warnMessage);
		}
		this.firstStep = true;
		this.insideAttributeTest = false;
		this.queryStack.push(new StringBuilder());
		if (this.verbose) System.out.println(this.queryStack.peek());
	}

	@Override
	public void exitPredicate(xpathRpqParser.PredicateContext ctx) {
		if (this.verbose) System.out.println("exitPredicate");
		StringBuilder predicate = this.queryStack.pop();
		if (this.insideAttributeTest) {
			this.insideAttributeTest = false;
			this.queryStack.peek().append("[");
			this.queryStack.peek().append(predicate.toString());
			this.queryStack.peek().append("]");
		} else {
			this.queryStack.peek().append("{");
			this.queryStack.peek().append(predicate.toString());
			this.queryStack.peek().append("}");
		}
		if (this.verbose) System.out.println(this.queryStack.peek());
	}

}
