package com.jrx.cloud.assembly.rule;

/**
 * 基础规则接口
 *
 * @author x
 * @version 1.0  2021/4/21
 */
public interface IRule {
    boolean execute(RuleDTO ruleDTO);
}
