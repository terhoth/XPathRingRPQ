package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class XPathRingListener extends xpathBaseListener {

    private StringBuilder query = new StringBuilder();

    private boolean firstStep = true;
    private boolean insidePredicate = false;
    private boolean isAttributeTest = false;

    private String axis = "";
    private String startEdge;
    private String endEdge;


    public void setQuery(Object s) {
    	this.query.append(s);
    }

    public Object getQuery() {
    	return query;
    }

    @Override
    public void exitMain(xpathParser.MainContext ctx) {
    	System.out.println("Ring query");
    	System.out.println(this.query);
    	System.out.println();
    	System.out.println("Translation done");
    }

    @Override
    public void enterRelativeLocationPath(xpathParser.RelativeLocationPathContext ctx) {
	System.out.println("enterRelativeLocationPath");
	this.firstStep = true;
	System.out.println(this.query);
    }

    @Override
    public void exitAxisSpecifier(xpathParser.AxisSpecifierContext ctx) {
	System.out.println("exitAxisSpecifier");
    	StringBuilder sb = new StringBuilder();
    	sb.append(ctx.getChild(0));

    	if (sb.toString().equals("parent")) {
    		this.axis = "parent";
    		this.startEdge = "<%";
    		this.endEdge = ">";
    	} else if (sb.toString().equals("ancestor")) {
    		this.startEdge = "(<%";
    		this.endEdge = ")+";
    		this.axis = "ancestor";
    	} else if (sb.toString().equals("child")) {
    		this.startEdge = "<";
    		this.endEdge = ">";
    		this.axis = "child";
    	} else if (sb.toString().equals("descendant")) {
    		this.startEdge = "(<";
    		this.endEdge = ">)+";
    		this.axis = "descendant";
    	} else if (sb.toString().equals("ancestor-or-self")) {
    		this.startEdge = "(<%";
    		this.endEdge = ">)*";
    		this.axis = "ancestor-or-self";
    	} else if (sb.toString().equals("descendant-or-self")) {
    		this.startEdge = "(<";
    		this.endEdge = ">)*";
    		this.axis = "descendant-or-self";
    	} else if (sb.toString().equals("attribute") || sb.toString().equals("@")) {
    		this.startEdge = "";
    		this.endEdge = "";
    		this.axis = "attribute";
		this.isAttributeTest = true;
    	} else if (sb.toString() == null || !sb.toString().isEmpty()) {
    		this.startEdge = "<";
    		this.endEdge = ">";
    		this.axis = "child";
    	} else {
    		throw new IllegalArgumentException("Unknown axis " + sb.toString());
    	}

	if (this.insidePredicate && this.firstStep) {
	    if (this.isAttributeTest) {
		this.query.append("[");
	    } else {
		this.query.append("{");
	    }
	}
	query.append(this.startEdge);
	System.out.println(this.query);
    }

    @Override
    public void enterStep(xpathParser.StepContext ctx) {
	System.out.println("enterStep");
	if (!this.firstStep) {
	    this.query.append("/");
	}
	System.out.println(this.query);
    }

    @Override
    public void exitStep(xpathParser.StepContext ctx) {
	System.out.println("exitStep");
	this.query.append(this.endEdge);
	this.firstStep = false;
	System.out.println(this.query);
    }

    @Override
    public void exitNCName(xpathParser.NCNameContext ctx) {
	System.out.println("exitNCName");
	this.query.append(ctx.getChild(0));
	System.out.println(this.query);
    }

    @Override
    public void enterPredicate(xpathParser.PredicateContext ctx) {
	System.out.println("enterPredicate");
	this.insidePredicate = true;
	this.isAttributeTest = false;
	this.firstStep = true;
	System.out.println(this.query);
    }

    @Override
    public void exitPredicate(xpathParser.PredicateContext ctx) {
	System.out.println("exitPredicate");
	this.insidePredicate = false;
	if (this.isAttributeTest) {
	    this.query.append("]");
	} else {
	    this.query.append("}");
	}
	System.out.println(this.query);
    }

}
