package com.example.demo;
// Generated from xpathRpq.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link xpathRpqParser}.
 */
public interface xpathRpqListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(xpathRpqParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(xpathRpqParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#locationPath}.
	 * @param ctx the parse tree
	 */
	void enterLocationPath(xpathRpqParser.LocationPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#locationPath}.
	 * @param ctx the parse tree
	 */
	void exitLocationPath(xpathRpqParser.LocationPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#absoluteLocationPathNoroot}.
	 * @param ctx the parse tree
	 */
	void enterAbsoluteLocationPathNoroot(xpathRpqParser.AbsoluteLocationPathNorootContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#absoluteLocationPathNoroot}.
	 * @param ctx the parse tree
	 */
	void exitAbsoluteLocationPathNoroot(xpathRpqParser.AbsoluteLocationPathNorootContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#relativeLocationPath}.
	 * @param ctx the parse tree
	 */
	void enterRelativeLocationPath(xpathRpqParser.RelativeLocationPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#relativeLocationPath}.
	 * @param ctx the parse tree
	 */
	void exitRelativeLocationPath(xpathRpqParser.RelativeLocationPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#step}.
	 * @param ctx the parse tree
	 */
	void enterStep(xpathRpqParser.StepContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#step}.
	 * @param ctx the parse tree
	 */
	void exitStep(xpathRpqParser.StepContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#axisSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterAxisSpecifier(xpathRpqParser.AxisSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#axisSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitAxisSpecifier(xpathRpqParser.AxisSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#nodeTest}.
	 * @param ctx the parse tree
	 */
	void enterNodeTest(xpathRpqParser.NodeTestContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#nodeTest}.
	 * @param ctx the parse tree
	 */
	void exitNodeTest(xpathRpqParser.NodeTestContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(xpathRpqParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(xpathRpqParser.PredicateContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#abbreviatedStep}.
	 * @param ctx the parse tree
	 */
	void enterAbbreviatedStep(xpathRpqParser.AbbreviatedStepContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#abbreviatedStep}.
	 * @param ctx the parse tree
	 */
	void exitAbbreviatedStep(xpathRpqParser.AbbreviatedStepContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(xpathRpqParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(xpathRpqParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(xpathRpqParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(xpathRpqParser.PrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(xpathRpqParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(xpathRpqParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#unionExprNoRoot}.
	 * @param ctx the parse tree
	 */
	void enterUnionExprNoRoot(xpathRpqParser.UnionExprNoRootContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#unionExprNoRoot}.
	 * @param ctx the parse tree
	 */
	void exitUnionExprNoRoot(xpathRpqParser.UnionExprNoRootContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#pathExprNoRoot}.
	 * @param ctx the parse tree
	 */
	void enterPathExprNoRoot(xpathRpqParser.PathExprNoRootContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#pathExprNoRoot}.
	 * @param ctx the parse tree
	 */
	void exitPathExprNoRoot(xpathRpqParser.PathExprNoRootContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#filterExpr}.
	 * @param ctx the parse tree
	 */
	void enterFilterExpr(xpathRpqParser.FilterExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#filterExpr}.
	 * @param ctx the parse tree
	 */
	void exitFilterExpr(xpathRpqParser.FilterExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(xpathRpqParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(xpathRpqParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(xpathRpqParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(xpathRpqParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(xpathRpqParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(xpathRpqParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(xpathRpqParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(xpathRpqParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(xpathRpqParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(xpathRpqParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(xpathRpqParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(xpathRpqParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#unaryExprNoRoot}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExprNoRoot(xpathRpqParser.UnaryExprNoRootContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#unaryExprNoRoot}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExprNoRoot(xpathRpqParser.UnaryExprNoRootContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#qName}.
	 * @param ctx the parse tree
	 */
	void enterQName(xpathRpqParser.QNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#qName}.
	 * @param ctx the parse tree
	 */
	void exitQName(xpathRpqParser.QNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(xpathRpqParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(xpathRpqParser.FunctionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#variableReference}.
	 * @param ctx the parse tree
	 */
	void enterVariableReference(xpathRpqParser.VariableReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#variableReference}.
	 * @param ctx the parse tree
	 */
	void exitVariableReference(xpathRpqParser.VariableReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#nameTest}.
	 * @param ctx the parse tree
	 */
	void enterNameTest(xpathRpqParser.NameTestContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#nameTest}.
	 * @param ctx the parse tree
	 */
	void exitNameTest(xpathRpqParser.NameTestContext ctx);
	/**
	 * Enter a parse tree produced by {@link xpathRpqParser#nCName}.
	 * @param ctx the parse tree
	 */
	void enterNCName(xpathRpqParser.NCNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link xpathRpqParser#nCName}.
	 * @param ctx the parse tree
	 */
	void exitNCName(xpathRpqParser.NCNameContext ctx);
}
