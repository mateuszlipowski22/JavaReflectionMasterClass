package reflection.section_7.graph;

import reflection.section_7.graph.annotations.Annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
//        BestGameFinder bestGameFinder = new BestGameFinder();
//
//        Set<String> games = bestGameFinder.getAllGames();
//
//        Map<String, Float> gameToRating = bestGameFinder.getGameToRating(games);
//        Map<String, Float> gameToPrice = bestGameFinder.getGameToPrice(games);
//
//        SortedMap<Double, String> scoreToGame = bestGameFinder.scoreGames(gameToPrice,gameToRating);
//
//        List<String> bestGamesInDescendingOrder = bestGameFinder.getTopGames(scoreToGame);
//
//        System.out.println(bestGamesInDescendingOrder);

        BestGameFinder bestGameFinder = new BestGameFinder();

        List<String> bestGamesInDescendingOrder = execute(bestGameFinder);

        System.out.println(bestGamesInDescendingOrder);
    }

    public static <T> T execute(Object instance) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = instance.getClass();

        Map<String,Method> operationToMethod = getOperationToMethod(clazz);

        Method finalResultMethod = findFinalResultMethod(clazz);

        return (T) executeWithDependencies(instance, finalResultMethod, operationToMethod);
    }

    private static Object executeWithDependencies(Object instance, Method currentMethod, Map<String, Method> operationToMethod) throws InvocationTargetException, IllegalAccessException {
        List<Object> parameterValues = new ArrayList<>(currentMethod.getParameterCount());

        for(Parameter parameter : currentMethod.getParameters()){
            Object value = null;
            if(parameter.isAnnotationPresent(DependsOn.class)){
                String dependencyOperationName = parameter.getAnnotation(DependsOn.class).value();
                Method dependencyMethod = operationToMethod.get(dependencyOperationName);
                value = executeWithDependencies(instance, dependencyMethod, operationToMethod);
            }
            parameterValues.add(value);
        }
        return currentMethod.invoke(instance, parameterValues.toArray());
    }

    private static Map<String, Method> getOperationToMethod(Class<?> clazz){
        Map<String, Method> operationNameToMethod = new HashMap<>();

        for(Method method : clazz.getDeclaredMethods()){
            if(!method.isAnnotationPresent(Operation.class)){
                continue;
            }
            Operation operation = method.getAnnotation(Operation.class);
            operationNameToMethod.put(operation.value(), method);
        }
        return operationNameToMethod;
    }

    private static Method findFinalResultMethod(Class<?> clazz){
        for(Method method : clazz.getDeclaredMethods()){
            if(method.isAnnotationPresent(FinalResult.class)){
                return method;
            }
        }
        throw new RuntimeException("No method found with FinalResult annotation");
    }
}
