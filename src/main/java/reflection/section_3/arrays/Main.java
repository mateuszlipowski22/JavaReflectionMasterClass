package reflection.section_3.arrays;

import java.lang.reflect.Array;

public class Main {
    public static void main(String[] args) {

        int[] oneDimensionArray = {1,2};
        double[][] twoDimensionArray = {{1.5,2.5},{1.7,2,5}};

        inspectArrayObject(oneDimensionArray);
        inspectArrayObject(twoDimensionArray);

        inspectArrayValues(oneDimensionArray);
        System.out.println();
        inspectArrayValues(twoDimensionArray);
    }

    public static void inspectArrayValues(Object arrayObject){
        int arrayLength = Array.getLength(arrayObject);
        System.out.print("[");
        for(int i=0 ; i < arrayLength ; i++){
            Object element = Array.get(arrayObject,i);

            if(element.getClass().isArray()){
                inspectArrayValues(element);
            }else {
                System.out.print(element);
            }

            if(i !=arrayLength-1){
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }

    public static void inspectArrayObject(Object arrayObject){
        Class<?> clazz = arrayObject.getClass();
        System.out.println(String.format("Is array : %s ", clazz.isArray()));

        Class<?> arrayComponentType = clazz.getComponentType();
        System.out.println(String.format("This is an array of type : %s ", arrayComponentType.getTypeName()));
    }
}
