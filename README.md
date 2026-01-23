# springSpelScroll
a simple language that relies on Spel for all expressions

## Explainer

I wanted to make a simple language that would glue together ***SpEL*** operations without the overhead of XML or JSON.  The language can be interpreted at runtime inside a Spring application, or interpreted as a shell script.

## Guiding Principles
* syntax to be reminiscent of the simplicity of **BASIC** or early **ADA.**
* interpreted by as *sha-bang* with referenced jars in a link referencing directory
* all expressions delegated to an underlying engine such as Spring Expression Language Interpreter

## Problems
1. Need to cleanly define a methodology for suggesting what is allowed in the sandbox
2. Clean method to describe parameter object
3. Change support from single quotes to double quotes so that spring expressions are handled properly

## Examples 
### Example 1 - A simple assignment
In order to demonstrate - here is a very simplified example (sorry for the pun).

* Spring Bean (to demonstrate the use of a SpEL involving  a spring bean)

      ```java
      package com.coteware.springscroll.spel;
    
      public class SpelDemoService {
        public String vavoom(String input) {
            return "vavoom----->" + input;
        }
      }
      ```

* Spring Configuration (integration of the spring bean vis a vis a Sandbox)
    ```java
    @TestConfiguration
    public class SpelTestConfiguration {
        /** the sandbox for the SpEL to reference existing Spring Beans */
        @Bean
        SpelServiceBeanResolver spelServiceBeanResolver(BeanFactory beanFactory) {
            BeanFactoryResolver beanFactoryResolver = new BeanFactoryResolver(beanFactory);
            return new SpelServiceBeanResolver(beanFactoryResolver);
        }
    
        /** launching the example spring bean to be used inside a SpEL */
        @Bean(name="spelDemoService") SpelDemoService spelDemoService() {
            return new SpelDemoService();
        }
    
    }
    ```

* Parameter Bean (used to pass parameters to a referenced spel without reevaluating an expression)
    ```java
    package com.coteware.springscroll.spel;
    
    public class SpelRoot {
        public SpelRoot() {
    
        }
        private Object parameter;
    
        public void setParameter(Object parameter) {
            this.parameter = parameter;
        }
    
        public Object getParameter() {
            return parameter;
        }
    }
    ```
* SpEL Script Source (a very contrived example demonstrating the concept)
    ```
    UNIT_NAME trial;
    DECLARE 
      foo STRING;
      bob STRING;
      bar INTEGER;
      barfloat FLOAT;
    BEGIN
      barfloat := 1.1;
      bar := 1;
      foo := 'BAR';
      bob := SPEL_START '@spelDemoService.vavoom(#root.getParameter())' foo SPEL_END;
      print bob,foo,SPEL_START 'new String('hello world!').toUpperCase()' SPEL_END ;
    END;
    
    ```
  * Evaluates like
    ```
    %vavoom----->BAR BAR HELLO WORLD! 
    ```

### Example 2 - a conditional assignment
<mark>**Not Yet Ready**</mark>
### Example 3 - a loop upon an expression
<mark>**Not Yet Ready**</mark>
### Example 4 - exception handling
<mark>**Not Yet Ready**</mark>