package com.coteware.springscroll.antlr;

import com.coteware.antlr.SpelScriptLexer;
import com.coteware.antlr.SpelScriptParser;
import com.coteware.springscroll.script.statements.Block;
import com.coteware.springscroll.script.ScopeMemory;
import com.coteware.springscroll.script.statements.Statement;
import com.coteware.springscroll.script.Scroll;
import com.coteware.springscroll.spel.SpelService;
import com.coteware.springscroll.spel.SpelServiceBeanResolver;
import com.coteware.springscroll.spel.SpelServiceImpl;
import com.coteware.springscroll.spel.SpelTestConfiguration;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.expression.spel.SpelParserConfiguration;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(SpelTestConfiguration.class)
class ScrollListenerTest {

    @Autowired
    private SpelServiceBeanResolver beanResolver;

    @Test
    void exitScroll() {
        String spelScriptContent = "UNIT_NAME trial; DECLARE foo STRING; bob STRING; bar INTEGER; barfloat FLOAT; BEGIN barfloat := 1.1; bar := 1; foo := \"BAR\"; bob := SPEL_START \"@spelDemoService.vavoom(#root.getParameter())\" foo SPEL_END; print bob,foo,SPEL_START \"new String('hello world').toUpperCase()\" SPEL_END ; END;";
        SpelScriptLexer spelScriptLexer = new SpelScriptLexer(CharStreams.fromString(spelScriptContent));
        CommonTokenStream tokens = new CommonTokenStream(spelScriptLexer);
        SpelScriptParser spelScriptParser = new SpelScriptParser(tokens);
        ParseTree tree = spelScriptParser.scroll();
        ParseTreeWalker walker = new ParseTreeWalker();

        SpelService spelService = new SpelServiceImpl(new SpelParserConfiguration(),beanResolver);

        Scroll scroll = new Scroll(spelService);
        ScrollListener scrollListener = new ScrollListener(scroll);
        walker.walk(scrollListener, tree);

        scrollListener.getMsgs().forEach(
                msg -> System.out.println(msg )
        );

        Optional<Block> maybeBlock = scrollListener.getCurrentBlock();
        assertTrue(maybeBlock.isPresent());
        Block block = maybeBlock.get();
        ScopeMemory scopeMemory = block.getScopeMemory();
        assertTrue(4 == scopeMemory.getDeclarationNames().size(),"expected 4 declarations - found " + scopeMemory.getDeclarationNames().size());
        Collection<Statement> statements = block.getStatements();
        assertTrue(5 == statements.size(),"expected 5 statements - found " + statements.size());

        assertTrue(1 == scroll.getUnits().size(),"expected 1 top level block - found " + scroll.getUnits().size());

        scroll.execute();

    }
}