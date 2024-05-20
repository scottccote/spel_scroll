package com.coteware.springscroll3.antlr;

import com.coteware.antlr.SpelScriptLexer;
import com.coteware.antlr.SpelScriptParser;
import com.coteware.springscroll3.script.ScopeMemory;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ScrollListenerTest {

    @Test
    void exitScroll() {
        String spelScriptContent = "DECLARE foo STRING; BEGIN foo := 'BAR'; END;";
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

        assertThat(scrollListener.getMsgs().size(), is(2));
        assertThat(scrollListener.getMsgs().get(0),
                is("DECLARE"));
        assertThat(scrollListener.getMsgs().get(1),
                is("BEGIN"));
    }
}