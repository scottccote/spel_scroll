package com.coteware.springscroll.casters;

import com.coteware.springscroll.script.ScopeMemory;
import com.coteware.springscroll.script.Scroll;
import com.coteware.springscroll.script.UnitResult;
import com.coteware.springscroll.script.UnitStatement;
import com.coteware.springscroll.script.statements.Block;
import com.coteware.springscroll.script.statements.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Wizard {
    private static final Logger log = LoggerFactory.getLogger(Wizard.class);
    public List<UnitResult> read(Scroll scroll) {
        return scroll.getUnits().stream()
                .map(unitStatement -> perform(unitStatement))
                .collect(Collectors.toList());

    }

    private UnitResult perform(UnitStatement unitStatement) {
        String unitName = unitStatement.getName();
        log.info("Performing " + unitName);
        Block block = unitStatement.getBlock();
        Statement statement = block.getStatements().getFirst();
        ScopeMemory scopeMemory = block.getScopeMemory();
        return perform(statement,scopeMemory);
    }

    private UnitResult perform(Statement statement,ScopeMemory scopeMemory) {
        return null;
    }
}
