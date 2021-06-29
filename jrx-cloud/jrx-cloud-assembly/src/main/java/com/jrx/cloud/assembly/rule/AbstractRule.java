package com.jrx.cloud.assembly.rule;

/**
 * 抽象规则（模板）
 *
 * @author x
 * @version 1.0  2021/4/21
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRule implements IRule {

    protected <T> T convert(RuleDTO ruleDTO) {
        return (T) ruleDTO;
    }

    protected <T> boolean executeRule(T t) {
        return true;
    }

    @Override
    public boolean execute(RuleDTO ruleDTO) {
        return executeRule(ruleDTO);
    }
}
