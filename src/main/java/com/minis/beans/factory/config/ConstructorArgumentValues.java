package com.minis.beans.factory.config;

import java.util.*;

public class ConstructorArgumentValues {
    private final Map<Integer, ConstructorArgumentValue> indexedArgumentValues =new HashMap<Integer, ConstructorArgumentValue>(0);
    private final List<ConstructorArgumentValue> argumentValueList = new ArrayList<ConstructorArgumentValue>();

    public ConstructorArgumentValues() {
    }

    private void addArgumentValue(Integer key, ConstructorArgumentValue argumentValue){
        this.indexedArgumentValues.put(key, argumentValue);
    }

    public void addArgumentValue(ConstructorArgumentValue argumentValue){
        this.argumentValueList.add(argumentValue);
    }

    public boolean hasIndexArgumentValue(int index){
        return this.indexedArgumentValues.containsKey(index);
    }

    public ConstructorArgumentValue getIndexArgumentValue(int index){
       // return this.indexedArgumentValues.get(index);
        return this.argumentValueList.get(index);
    }

    public void addGenericArgumentValue(Object value, String type){
        this.argumentValueList.add(new ConstructorArgumentValue(value,type));
    }

    private void addGenericArgumentValue(ConstructorArgumentValue newValue){
        if (newValue.getName()!=null){
            for (Iterator<ConstructorArgumentValue> it = this.argumentValueList.iterator(); it.hasNext();){
                ConstructorArgumentValue currentValue = it.next();
                if(newValue.getName().equals(currentValue.getName())){
                    it.remove();
                }
            }
        }
        this.argumentValueList.add(newValue);
    }

    public ConstructorArgumentValue getGenericArgumentValue(String requiredName){
        for (ConstructorArgumentValue valueHolder :
                this.argumentValueList) {
            if (valueHolder.getName()!=null && !valueHolder.getName().equals(requiredName)){
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount(){
        return this.argumentValueList.size();
    }

    public boolean isEmpty(){
        return this.argumentValueList.isEmpty();
    }
}
