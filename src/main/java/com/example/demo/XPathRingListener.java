package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class XPathRingListener extends xpathBaseListener {

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


    private Output output = new Output();

    public void setQuery(Object s) {
    	this.query.append(s);
    }

    public Object getQuery() {
    	return this.query;
    }

    @Override
    public void enterMain(xpathParser.MainContext ctx) {
	this.queryStack.push(query);    
    }

    @Override
    public void exitMain(xpathParser.MainContext ctx) {
    	System.out.println("Ring query");
	this.query = this.queryStack.pop();
    	System.out.println(this.query);
    	System.out.println();
    	System.out.println("Translation done");
    }

    @Override 
    public void exitEqualityExpr(xpathParser.EqualityExprContext ctx) {
	System.out.println("exitEqualityExpr");
	if (ctx.getChildCount() > 1)
	{
	    this.queryStack.peek().append(" " + ctx.getChild(1) + " "); // operator
	    this.queryStack.peek().append(this.attributeValue.toString());
	}
	System.out.println(this.queryStack.peek());
    }

    @Override 
    public void exitRelationalExpr(xpathParser.RelationalExprContext ctx) {
	System.out.println("exitRelationalExpr");
	if (ctx.getChildCount() > 1)
	{
	    this.queryStack.peek().append(" " + ctx.getChild(1) + " "); // operator
	    this.queryStack.peek().append(this.attributeValue.toString());
	}
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void exitPrimaryExpr(xpathParser.PrimaryExprContext ctx) {
	System.out.println("exitPrimaryExpr");
    	//Checking if the child node is leaf node
    	if (ctx.getChild(0).getChild(0) == null) {
    		this.attributeValue = ctx.getChild(0);
    	}
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void enterRelativeLocationPath(xpathParser.RelativeLocationPathContext ctx) {
	System.out.println("enterRelativeLocationPath");
	this.firstStep = true;
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void exitRelativeLocationPath(xpathParser.RelativeLocationPathContext ctx) {
	System.out.println("exitRelativeLocationPath");
	if (this.queryStack.size() == 1) {
	    // this is the end of the main query
	    int n_of_steps = (ctx.getChildCount() + 1) / 2;
	    if (n_of_steps % 2 == 0 || this.deletedStep) {
		// no vertex endpoint specified
		this.queryStack.peek().append(" ?y");
	    } else {
		// put a space before the last vertex, and remove ¤
		int i = this.queryStack.peek().lastIndexOf("/<¤");
		this.queryStack.peek().delete(i, i+3);
		this.queryStack.peek().insert(i, " <");
	    }
	}
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void exitAxisSpecifier(xpathParser.AxisSpecifierContext ctx) {
	System.out.println("exitAxisSpecifier");
    	StringBuilder sb = new StringBuilder();
    	sb.append(ctx.getChild(0));

    	if (sb.toString().equals("parent")) {
    		this.axis.push("parent");
    		this.startEdge.push("<%");
    		this.endEdge.push(">");
    	} else if (sb.toString().equals("ancestor")) {
    		this.startEdge.push("(<%");
    		this.endEdge.push(">)+");
    		this.axis.push("ancestor");
    	} else if (sb.toString().equals("child")) {
    		this.startEdge.push("<");
    		this.endEdge.push(">");
    		this.axis.push("child");
    	} else if (sb.toString().equals("descendant")) {
    		this.startEdge.push("(<");
    		this.endEdge.push(">)+");
    		this.axis.push("descendant");
    	} else if (sb.toString().equals("ancestor-or-self")) {
    		this.startEdge.push("(<%");
    		this.endEdge.push(">)*");
    		this.axis.push("ancestor-or-self");
    	} else if (sb.toString().equals("descendant-or-self")) {
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
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void enterStep(xpathParser.StepContext ctx) {
	System.out.println("enterStep");
	if (!this.firstStep && this.queryStack.peek().charAt(this.queryStack.peek().length()-1) != ' ') {
	    this.queryStack.peek().append("/");
	}
	this.deletedStep = false;
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void exitStep(xpathParser.StepContext ctx) {
	System.out.println("exitStep");
	this.queryStack.peek().append(this.endEdge.peek());
	
	if (firstStep && this.queryStack.size() == 1) {
	    // this is the very first vertex of the main query
	    if (this.axis.peek() != "child")  {
	        String warnMessage = "The first step should contain only a vertex name (or wildcard vertex)";
    	        String details = this.queryStack.peek().toString();
    	        output.printWarning(warnMessage, details);
	    }
	    if (this.queryStack.peek().toString().equals("<¤>")) {
		// replace unspecified start vertex with variable
		this.queryStack.pop();
	        this.queryStack.push(new StringBuilder("?x"));
	    } else {
		// remove ¤ from constant start vertex
		this.queryStack.peek().deleteCharAt(1); 
	    }
	    this.queryStack.peek().append(" ");

	} else {
	    // remove the step if it was just a nameless vertex
	    String uselessStep = "/" + this.startEdge.peek() + "¤" + this.endEdge.peek();
	    int i = this.queryStack.peek().indexOf(uselessStep);
	    if (i != -1) {
	        this.queryStack.peek().delete(i, this.queryStack.peek().length());
		this.deletedStep = true;
	    }
	    // warn of nameless edges
	    uselessStep = "/" + this.startEdge.peek() + this.endEdge.peek();
	    i = this.queryStack.peek().indexOf(uselessStep);
	    if (i != -1) {
	        String warnMessage = "Nameless edges can be costly in evaluation. Besides, they have not been implemented yet!";
    	        String details = uselessStep;
    	        output.printWarning(warnMessage, details);
	    }
	}

	this.axis.pop();
	this.startEdge.pop();
	this.endEdge.pop();
	this.firstStep = false;
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void enterNameTest(xpathParser.NameTestContext ctx) {
	System.out.println("enterNameTest");
	this.nCName = "";
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void exitNameTest(xpathParser.NameTestContext ctx) {
	System.out.println("exitNameTest");
	int n_of_steps = (ctx.parent.parent.parent.getChildCount() + 1) / 2;
	// main query begins with a vertex, predicates with an edge
	if ((this.queryStack.size() == 1 && n_of_steps % 2 == 1) ||
	    (this.queryStack.size() >  1 && n_of_steps % 2 == 0))
	{
	    this.queryStack.peek().append('¤');
	}
	this.queryStack.peek().append(this.nCName);
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void exitNCName(xpathParser.NCNameContext ctx) {
	System.out.println("exitNameTest");
	StringBuilder sb = new StringBuilder();
    	this.nCName = sb.append(ctx.getChild(0)).toString();	
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void enterPredicate(xpathParser.PredicateContext ctx) {
	System.out.println("enterPredicate");
	this.firstStep = true;
	this.insideAttributeTest = false;
	this.queryStack.push(new StringBuilder());
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void exitPredicate(xpathParser.PredicateContext ctx) {
	System.out.println("exitPredicate");
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
	System.out.println(this.queryStack.peek());
    }

}
