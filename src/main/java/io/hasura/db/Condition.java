package io.hasura.db;

import com.google.gson.*;

class Condition<R> {
    private JsonObject boolExp;

    JsonObject getBoolExp() {
        return this.boolExp;
    }

    Condition(JsonObject boolExp) {
        this.boolExp = boolExp;
    }

    public Condition<R> and(Condition<R> c2) {
        JsonObject newBoolExp = new JsonObject ();
        JsonArray andExpArr = new JsonArray ();
        andExpArr.add(this.boolExp);
        andExpArr.add(c2.getBoolExp());
        newBoolExp.add("$and", andExpArr);
        return new Condition<R>(newBoolExp);
    }

    public Condition<R> or(Condition<R> c2) {
        JsonObject newBoolExp = new JsonObject ();
        JsonArray orExpArr = new JsonArray ();
        orExpArr.add(this.boolExp);
        orExpArr.add(c2.getBoolExp());
        newBoolExp.add("$or", orExpArr);
        return new Condition<R>(newBoolExp);
    }
}
