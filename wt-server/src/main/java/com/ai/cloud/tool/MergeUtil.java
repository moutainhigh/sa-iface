package com.ai.cloud.tool;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @author wave
 * @create 2018-06-20 11:24
 **/
public class MergeUtil {

    /**
     * @author wave
     * 把 sourceList 的一些属性合并到 targetList 中
     * 基于 testFunction 的条件,合入逻辑实现为 biConsumer
     * @param targetList
     * @param sourceList
     * @param testFunction
     * @param biConsumer
     * @param <T>
     * @param <S>
     */
    public static <T, S> void merge(List<T> targetList, List<S> sourceList,
                                    BiFunction<? super T, ? super S,Boolean> testFunction,
                                    BiConsumer<? super T, ? super S> biConsumer) {
        targetList.forEach((t)->{
            Optional<S> optional = sourceList.stream().filter(s -> testFunction.apply(t,s)).findFirst();
            if (optional.isPresent()) {
                biConsumer.accept(t,optional.get());
            }
        });
    }
}
