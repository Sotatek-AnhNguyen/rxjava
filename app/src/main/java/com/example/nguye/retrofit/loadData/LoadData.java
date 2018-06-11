package com.example.nguye.retrofit.loadData;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.nguye.retrofit.Connect.ApiUtils;
import com.example.nguye.retrofit.Connect.ReService;
import com.example.nguye.retrofit.Model.DataLogin;
import com.example.nguye.retrofit.Model.Datum;
import com.example.nguye.retrofit.Model.ReData;
import com.example.nguye.retrofit.Model.Todo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nguye on 29/05/2018.
 */

public class LoadData {
    private ReService reService;
    private LoadDataTodo loadDataTodo;
    private LoadDataReAToddo loadDataReAToddo;
    private LoadDataLogin loadDataLogin;
    private Gson gson;

    public LoadData(LoadDataTodo loadDataTodo) {
        this.loadDataTodo = loadDataTodo;
        reService = ApiUtils.getReService();
        gson = new Gson();
    }

    public LoadData(LoadDataReAToddo loadDataReAToddo) {
        this.loadDataReAToddo = loadDataReAToddo;
        reService = ApiUtils.getReService();
        gson = new Gson();
    }

    public LoadData(LoadDataLogin loadDataLogin) {
        this.loadDataLogin = loadDataLogin;
        reService = ApiUtils.getReService();
    }

    public void getDataMain(String au){

        reService.getAu(au).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Todo>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadDataTodo.onFail(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Todo todo) {

                        ArrayList<Datum> arrData = new ArrayList<>();
                        for (int i = 0; i< todo.getData().size(); i++) {
                            Datum datum = gson.fromJson(todo.getData().get(i), Datum.class);
                            arrData.add(datum);
                        }

                        loadDataTodo.onSuccessTodo(arrData);
                    }
                });
        
    }

    public void getLogin(String email, String pass){

        reService.login(email, pass).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe(new Observer<ReData<DataLogin>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                loadDataLogin.onFail(e.getLocalizedMessage());
            }

            @Override
            public void onNext(ReData<DataLogin> dataLoginReData) {
                loadDataLogin.onSuccessLogin(dataLoginReData);
            }
        });
    }

    public void getRegister(String email, String pass, String name){

        reService.register(email, pass, name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe(new Observer<ReData<Datum>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ReData<Datum> datumReData) {
                if (datumReData.getSuccess()){
                    loadDataReAToddo.onSuccessLogin(datumReData);

                }else {

                }
            }
        });
    }

    public void createATodo(String title, String au){

        reService.createATodo(title, au).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe(new Observer<ReData<Datum>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ReData<Datum> datumReData) {
                loadDataReAToddo.onSuccessLogin(datumReData);
            }
        });
    }

    public void updateATodo(String id, String title, String au){

        reService.update(id, title, au).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe(new Observer<ReData<Datum>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ReData<Datum> datumReData) {
                loadDataReAToddo.onSuccessLogin(datumReData);
            }
        });
    }

    public void deleteATodo(String id, String au){

        reService.deleteATodo(id, au).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe(new Observer<ReData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ReData reData) {
                loadDataReAToddo.onSuccessLogin(reData);
            }
        });
    }
}
