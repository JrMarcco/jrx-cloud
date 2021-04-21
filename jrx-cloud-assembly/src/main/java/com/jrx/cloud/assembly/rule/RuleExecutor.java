package com.jrx.cloud.assembly.rule;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author x
 * @version 1.0  2021/4/21
 */
@Slf4j
public class RuleExecutor {

    private final Map<Integer, List<IRule>> ruleMap = new HashMap<>();
    private static final int AND = 1;
    private static final int OR = 0;

    public static RuleExecutor create() {
        return new RuleExecutor();
    }

    public RuleExecutor and(List<IRule> ruleList) {
        ruleMap.put(AND, ruleList);
        return this;
    }

    public RuleExecutor or(List<IRule> ruleList) {
        ruleMap.put(OR, ruleList);
        return this;
    }

    public boolean execute(RuleDTO dto) {
        for (Map.Entry<Integer, List<IRule>> item : ruleMap.entrySet()) {
            List<IRule> ruleList = item.getValue();
            switch (item.getKey()) {
                case AND:
                    // 如果是 and 关系，同步执行
                    log.info("### execute key = 1 ###");
                    if (!and(dto, ruleList)) {
                        return false;
                    }
                    break;
                case OR:
                    // 如果是 or 关系，并行执行
                    log.info("### execute key = 0 ###");
                    if (!or(dto, ruleList)) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    // ----------------------------------------< Private Method >----------------------------------------
    private boolean and(RuleDTO dto, List<IRule> ruleList) {
        for (IRule rule : ruleList) {
            if (!rule.execute(dto)) {
                return false;
            }
        }
        return true;
    }

    private boolean or(RuleDTO dto, List<IRule> ruleList) {
        for (IRule rule : ruleList) {
            if (rule.execute(dto)) {
                return true;
            }
        }
        return false;
    }
}
