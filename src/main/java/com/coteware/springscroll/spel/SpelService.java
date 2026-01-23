package com.coteware.springscroll.spel;

import java.util.Optional;

public interface SpelService {
    Optional<?> evaluate(
            String expression
            , SpelRoot spelRoot
    );
}
