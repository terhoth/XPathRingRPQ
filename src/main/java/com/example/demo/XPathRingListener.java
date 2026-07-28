package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class XPathRingListener extends xpathBaseListener {

    private StringBuilder query = new StringBuilder();

    private boolean firstStep = true;

    private Stack<StringBuilder> queryStack = new Stack<StringBuilder>();
    private Stack<String> axis = new Stack<String>();
    private Stack<String> startEdge = new Stack<String>();
    private Stack<String> endEdge = new Stack<String>();

    private boolean insideAttributeTest = false;
    private Object attributeValue;


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
    	//Checking if the child node is leaf node
    	if (ctx.getChild(0).getChild(0) == null) {
    		this.attributeValue = ctx.getChild(0);
    	}
    }

    @Override
    public void enterRelativeLocationPath(xpathParser.RelativeLocationPathContext ctx) {
	System.out.println("enterRelativeLocationPath");
	this.firstStep = true;
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
    		this.endEdge.push(")+");
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
	if (!this.firstStep) {
	    this.queryStack.peek().append("/");
	}
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void exitStep(xpathParser.StepContext ctx) {
	System.out.println("exitStep");
	this.queryStack.peek().append(this.endEdge.peek());
	this.axis.pop();
	this.startEdge.pop();
	this.endEdge.pop();
	this.firstStep = false;
	System.out.println(this.queryStack.peek());
    }

    @Override
    public void exitNCName(xpathParser.NCNameContext ctx) {
	System.out.println("exitNCName");
	this.queryStack.peek().append(ctx.getChild(0));
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
