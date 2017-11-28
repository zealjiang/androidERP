package com.jzg.erp.utils;

import android.support.annotation.Nullable;

/**
 * Created by wangyd on 16/6/23.
 * 本类用于校验方法中使用的对象是否为空、数组是否越界、表达式是否合法(如有其他校验添加请在注释中说明)
 */
public final class Preconditions {
    private Preconditions() {
    }

    /**
     * 校验对象是否为空
     *
     * @param obj 对象引用
     * @return 非空对象
     */
    public static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    /**
     * 校验对象是否为空
     *
     * @param obj      对象引用
     * @param errorMsg 错误信息
     * @return 非空对象
     */
    public static <T> T checkNotNull(T obj, @Nullable Object errorMsg) {
        if (obj == null) {
            throw new NullPointerException(String.valueOf(errorMsg));
        }
        return obj;
    }

    /**
     * 校验index在数组、列表、字符串中是否合法
     *
     * @param index 下标位置
     * @param size  以上3中类型的长度
     * @return 返回有效的下标
     */
    public static int checkElementIndex(int index, int size) {
        if (size < 0) {
            throw new IllegalArgumentException("非法长度: index=" + index + ",size=" + size);
        }

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("下标越界：index=" + index + ",size=" + size);
        }
        return index;
    }

    /**
     * 校验某个表达式是否合法如:1、Array.length==8 2、int >= 0 3、List.isEmpty()
     *
     * @param expression
     * @param errorMsg
     */
    public static void checkArgument(boolean expression, @Nullable String errorMsg) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMsg));
        }
    }

}
