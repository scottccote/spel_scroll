package com.coteware.springscroll3.script;

import com.coteware.springscroll3.script.declarations.DeclarationSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ScopeMemory {
    final Map<String, DeclarationSpec> memory = new HashMap<>();

    public ScopeMemory() {
    }

    public ScopeMemory(final ScopeMemory parentScopeScopeMemory) {
        memory.putAll(parentScopeScopeMemory.memory);
    }

    public void add(DeclarationSpec declarationSpec) {
        memory.put(declarationSpec.getName(),declarationSpec);
    }

    public Optional<DeclarationSpec> get(String name) {
        return Optional.ofNullable(memory.get(name));
    }

    public void clear() {
        memory.clear();
    }
}
