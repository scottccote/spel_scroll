package com.coteware.springscroll.script;

import com.coteware.springscroll.spel.SpelService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scroll {
    private List<UnitStatement> units = new ArrayList<>();
    private final SpelService spelService;

    public Scroll(SpelService spelService) {
        this.spelService = spelService;
    }

    public void add(UnitStatement unit) {
        units.add(unit);
    }

    public List<UnitStatement> getUnits() {
        return Collections.unmodifiableList(units);
    }

    public void execute() {
        units.forEach(unit -> {
            unit.setSpelService(spelService);
            unit.execute();
        });
    }
}
