package com.semicolon.scientificresearch.SingleTone;

import com.semicolon.scientificresearch.Models.UserModel;

public class UserSingleTone {
    private static UserSingleTone instance=null;
    private static UserModel userModel;
    private UserDataInterface userDataInterface;
    private UserSingleTone(){}

    public static synchronized UserSingleTone getInstance()
    {
        if (instance==null)
        {
            instance = new UserSingleTone();
        }
        return instance;
    }

    public interface UserDataInterface
    {
        void getUserData(UserModel userModel);
    }
    public void SetUserData(UserModel userModel)
    {
        this.userModel = userModel;
    }
    public void GetUserData(UserDataInterface userDataInterface)
    {
        this.userDataInterface = userDataInterface;
        this.userDataInterface.getUserData(userModel);
    }

    public void Clear()
    {
        userModel=null;
        SetUserData(userModel);
    }
}
