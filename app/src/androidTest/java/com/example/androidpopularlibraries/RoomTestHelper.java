package com.example.androidpopularlibraries;

import com.example.androidpopularlibraries.model.RoomModel;
import com.example.androidpopularlibraries.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class RoomTestHelper {

    private static List<UserModel> userModelList = new ArrayList<>();

    public static List<RoomModel> createListOfUsers(int quantity) {
        List<RoomModel> roomModelList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            roomModelList.add(new RoomModel());
        }
        return roomModelList;
    }

    public static boolean listsAreIdentical(List<RoomModel> l1, List<RoomModel> l2) {
        if (l1.size() != l2.size())  return false;

        for (int i = 1; i < l1.size(); i++) {
            if (!l1.get(i).equals(l2.get(i))) { // сравниваем всё, кроме id
                return false;
            }
        }
        return true;
    }
}
