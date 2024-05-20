package com.coteware.springscroll3.antlr;

import com.coteware.antlr.SpelScriptLexer;
import com.coteware.antlr.SpelScriptParser;
import com.coteware.springscroll3.script.Block;
import com.coteware.springscroll3.script.ScopeMemory;
import com.coteware.springscroll3.script.statements.Statement;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ScrollListenerTest {

    @Test
    void exitScroll() {
        String spelScriptContent = "DECLARE foo STRING; bob STRING; bar INTEGER; barfloat FLOAT; BEGIN barfloat := 1.1; bar := 1; foo := 'BAR'; bob := SPEL_START 'fooobarrr' foo SPEL_END; END;";
        SpelScriptLexer spelScriptLexer = new SpelScriptLexer(CharStreams.fromString(spelScriptContent));
        CommonTokenStream tokens = new CommonTokenStream(spelScriptLexer);
        SpelScriptParser spelScriptParser = new SpelScriptParser(tokens);
        ParseTree tree = spelScriptParser.scroll();
        ParseTreeWalker walker = new ParseTreeWalker();

        ScrollListener scrollListener = new ScrollListener();
        walker.walk(scrollListener, tree);

        scrollListener.getMsgs().forEach(
                msg -> System.out.println(msg )
        );

        Optional<Block> maybeBlock = scrollListener.getCurrentBlock();
        assertTrue(maybeBlock.isPresent());
        Block block = maybeBlock.get();
        ScopeMemory scopeMemory = block.getScopeMemory();
        assertTrue(4 == scopeMemory.getDeclarationNames().size());
        Collection<Statement> statements = block.getStatements();
        assertTrue(4 == statements.size());
    }
}