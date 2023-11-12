package com.featuresketch.featuresketchcreator;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.YamlPrinter;
import com.github.javaparser.resolution.Navigator;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.contexts.ClassOrInterfaceDeclarationContext;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileParser {
    private static final String FILE_PATH = "src/main/java/org/javaparser/samples/ReversePolishNotation.java";
    private static final String SRC_PATH = "src/main/java/org/javaparser/samples/";
    private List<String> fieldList = new ArrayList<>();
    private Map<String, Map<String, Integer>> usage = new HashMap<>();

    public void parseFile() throws Exception {
        TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(SRC_PATH));
        CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
        combinedSolver.add(javaParserTypeSolver);
        combinedSolver.add(new ReflectionTypeSolver());
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver);
        StaticJavaParser.getParserConfiguration().setSymbolResolver(symbolSolver);
        CompilationUnit cu = StaticJavaParser.parse(Files.newInputStream(Paths.get(FILE_PATH)));

        List<FieldDeclaration> fields = cu.findAll(FieldDeclaration.class);
        List<NameExpr> nameExpressions = cu.findAll(NameExpr.class);

        YamlPrinter printer = new YamlPrinter(true);
        for(NameExpr nameExpr: nameExpressions) {
//            System.out.println(printer.output(nameExpr));
            System.out.println(nameExpr.getName());
            if(!nameExpr.getName().toString().equals("Stream")) {
                 if(nameExpr.isClassExpr()) {
                     System.out.println("FOUND");
                 }

            }
        }

//        for(FieldDeclaration fieldDeclaration: fields) {
//            fieldList.add(fieldDeclaration.resolve().getName());
//        }
//        System.out.println(fieldList);



//        VoidVisitor<List<String>> classVisitor = new ClassPrinter();
//        classVisitor.visit(cu, fieldList);
//        YamlPrinter printer = new YamlPrinter(true);
//        System.out.println(printer.output(cu));

//        VoidVisitor<List<String>> fieldDeclarationVisitor = new FieldDeclarationPrinter();
//        fieldDeclarationVisitor.visit(cu, fieldList);

//        VoidVisitor<Void> methodNameVistor = new MethodNamePrinter(fieldList, usage);
//        methodNameVistor.visit(cu, null);

//        VoidVisitor<Void> nameExpVistor = new NameExpPrinter();
//        nameExpVistor.visit(cu, null);

//        VoidVisitor<Void> fieldAccessExprVistor = new FieldAccessExprPrinter();
//        fieldAccessExprVistor.visit(cu, null);

//        VoidVisitor<Void> methodTester = new MethodTester();
//        methodTester.visit(cu, null);

    }


    private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {

        private List<String> fieldList;
        private Map<String, Map<String, Integer>> usage;

        public MethodNamePrinter(List<String> fieldList, Map<String, Map<String, Integer>> usage) {
            super();
            this.fieldList = fieldList;
            this.usage = usage;
        }

        @Override
        public void visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            System.out.println("\nMethod Name Printed: " + md.getName());
//            for(Statement statement: md.getBody().get().getStatements()) {
//                System.out.println(statement);
//            }





            VoidVisitor<Void> fieldAccessExpVistor = new FieldAccessExprPrinter();
            fieldAccessExpVistor.visit(md, null);

        }
    }

    private static class MethodTester extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            YamlPrinter printer = new YamlPrinter(true);
            System.out.println(printer.output(md));

        }
    }

    private static class ClassPrinter extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration cd, List<String> collector) {
            super.visit(cd, collector);
            //System.out.println("Method Name Printed: " + cd.getName());

            for(FieldDeclaration fieldDeclaration: cd.getFields()) {

                String[] test = fieldDeclaration.getVariables().toString().split(" ");
                String varName = Arrays.stream(test).toList().get(0).substring(1);
                System.out.println(varName);

            }

        }
    }

    private static class NameExpPrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(NameExpr ne, Void arg) {
            super.visit(ne, arg);

            if (ne.resolve().isField()) {
                System.out.println(ne.getName());
            }

        }
    }

    private static class FieldAccessExprPrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(FieldAccessExpr fae, Void arg) {
            super.visit(fae, arg);
            System.out.println(fae.getName());

        }
    }

    private static class FieldDeclarationPrinter extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(FieldDeclaration fd, List<String> collector) {
            super.visit(fd, collector);
            String[] test = fd.getVariables().toString().split(" ");
            String varName = Arrays.stream(test).toList().get(0).substring(1);
            collector.add(varName);


        }
    }

}
