package com.coteware.springscroll.script;

import com.coteware.springscroll.script.statements.Block;
import com.coteware.springscroll.spel.SpelService;

public class UnitStatement {
    private final Block block;
    private final String name;

    public UnitStatement(Block block, String name) {
        this.block = block;
        this.name = name;
    }

    public Block getBlock() {
        return block;
    }

    public String getName() {
        return name;
    }

    public void setSpelService(SpelService spelService) {
        getBlock().setSpelService(spelService);
    }

    public void execute() {
        getBlock().execute();
    }
}
