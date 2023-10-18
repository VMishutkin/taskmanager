package com.vmish.taskmanager.model;

/**
 * Перечисления для статусов задачи с ортдельной строкой для отображения
 */
public enum Status {
    NEW ("Новая"),
    ACCEPTED("В работе"),
    CLOSED("Закрыта");
    private String statusName;
    Status(String statusName){
        this.statusName = statusName;
    }
    public String getStatusName(){
        return statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }
}
