package io.github.lfcamarati.resthateoas.core;

import io.github.lfcamarati.resthateoas.core.jsonplaceholder.Comment;
import javassist.ClassPool;
import javassist.CtClass;
import org.junit.jupiter.api.Test;

public class Javassist {

    @Test
    void testJavassist() throws Exception {
        Comment comment = new Comment();

        Class<?> aClass = comment.getClass();
        ClassLoader classLoader = aClass.getClassLoader();

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(aClass.getName());
        cc.setInterfaces(new CtClass[] {pool.get(Resource.class.getName())});

        cc.writeFile(".");
    }
}
