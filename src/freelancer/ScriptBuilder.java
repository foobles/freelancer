package freelancer;

import java.util.ArrayList;
import java.util.function.Function;

public final class ScriptBuilder<Env> {
    private final ArrayList<Script<Env>> scriptList = new ArrayList<>();

    public ScriptBuilder() {}

    public ScriptBuilder<Env> thenPlay(Script<Env> script) {
        scriptList.add(script);
        return this;
    }

    public ScriptBuilder<Env> thenSay(String name, String... lines) {
        return this.thenPlay(new ScriptLine<>(name, lines));
    }

    public ScriptBuilder<Env> thenNarrate(String... lines) {
        return this.thenSay("", lines);
    }

    public ScriptBuilder<Env> thenCond(Function<CondBuilder<Env>, CondBuilder<Env>> buildCond) {
        return this.thenPlay(buildCond.apply(new CondBuilder<>()).build());
    }

    public Script<Env> build() {
        if (scriptList.size() == 1) {
            return scriptList.get(0);
        } else {
            return new ScriptList<>(scriptList);
        }
    }

    public static final class CondBuilder<Env> {
        private final ArrayList<GameCondition<Env>> scriptConditions = new ArrayList<>();
        private final ArrayList<Script<Env>> scripts = new ArrayList<>();
        private Script<Env> otherwise = null;

        public CondBuilder<Env> playIf(GameCondition<Env> cond, Function<ScriptBuilder<Env>, ScriptBuilder<Env>> bf) {
            assert scripts.size() == scriptConditions.size();
            assert otherwise == null;
            scripts.add(bf.apply(new ScriptBuilder<>()).build());
            scriptConditions.add(cond);
            return this;
        }

        public CondBuilder<Env> otherwise(Function<ScriptBuilder<Env>, ScriptBuilder<Env>> bf) {
            otherwise = bf.apply(new ScriptBuilder<>()).build();
            return this;
        }

        public ScriptCond<Env> build() {
            final int oldSize = scripts.size();
            if (otherwise != null) {
                scripts.add(otherwise);
            }
            return new ScriptCond<>(
                    (p, e) -> {
                        for (int i = 0; i < oldSize; ++i) {
                            if (scriptConditions.get(i).test(p, e)) {
                                return i;
                            }
                        }
                        return otherwise == null? -1 : oldSize;
                    },
                    scripts
            );
        }


    }
}
