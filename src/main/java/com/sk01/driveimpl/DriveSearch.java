package com.sk01.driveimpl;

import com.sk01.storage.Search;

import java.io.File;
import java.sql.Time;
import java.util.List;

public class DriveSearch extends Search {

    @Override
    public void getFile(String path) throws Exception {

    }

    @Override
    public void getFiles(String extension) {

    }

    @Override
    public void getFilesWithExtension(String extension) throws Exception {

    }

    @Override
    public void getAllFiles(String path) throws Exception {

    }

    @Override
    public boolean containsFiles(String path, List fileNames) throws Exception {
        return false;
    }

    @Override
    public File getDir(String fileName) {
        return null;
    }

    @Override
    public void sortByName(List files) {

    }

    @Override
    public void sortByDate(List files) {

    }

    @Override
    public void getFiles(String path, Time pocetak, Time kraj) throws Exception {

    }

    @Override
    public void filtrate(String string) {

    }
}
