package com.sk01.driveimpl;

import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.File;
import com.sk01.storage.Search;
import com.sk01.utils.StorageInfo;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DriveSearch extends Search {

    @Override
    public java.io.File getFile(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        File dir = GoogleDrive.getFile(path);

        String name = dir.getName();
        java.io.File newFile = new java.io.File(name);
        return newFile;
    }

    @Override
    public List<java.io.File> getFiles(String podstring) throws Exception {
        String path = StorageInfo.getInstance().getConfig().getPath();

        File dir = GoogleDrive.getFile(path);
        String query = "parents=" + "'" + dir.getId() + "'";
        FileList list = GoogleDrive.service.files().list().setQ(query).setFields("nextPageToken, files(id, name, createdTime, mimeType, modifiedTime, parents, fileExtension)").execute();

        List<File> files = new ArrayList<>();
        for (File file: list.getFiles()) {
            if (!file.getMimeType().equals("application/vnd.google-apps.folder")) {
                if (file.getName().contains(podstring)) {
                    files.add(file);
                }
            }
        }

        return getJavaFiles(files);
    }

    @Override
    public List<java.io.File> getFilesWithExtension(String extension) throws Exception {
        String path = StorageInfo.getInstance().getConfig().getPath();

        File dir = GoogleDrive.getFile(path);
        String query = "parents=" + "'" + dir.getId() + "'";
        FileList list = GoogleDrive.service.files().list().setQ(query).setFields("nextPageToken, files(id, name, createdTime, mimeType, modifiedTime, parents, fileExtension)").execute();

        List<File> files = new ArrayList<>();
        for (File file: list.getFiles()) {
            if (!file.getMimeType().equals("application/vnd.google-apps.folder")) {
                if (file.getName().endsWith(extension)) {
                    files.add(file);
                }
            }
        }

        return getJavaFiles(files);
    }

    @Override
    public List<java.io.File> getAllFiles(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        File dir = GoogleDrive.getFile(path);
        String query = "parents=" + "'" + dir.getId() + "'";
        FileList list = GoogleDrive.service.files().list().setQ(query).setFields("nextPageToken, files(id, name, createdTime, mimeType, modifiedTime, parents, fileExtension)").execute();

        List<File> files = new ArrayList<>();
        for (File file: list.getFiles()) {
            if (!file.getMimeType().equals("application/vnd.google-apps.folder")) {
                files.add(file);
            }
        }

        return getJavaFiles(files);
    }

    @Override
    public List<java.io.File> getAllFiles() throws Exception {
        String path = StorageInfo.getInstance().getConfig().getPath();

        File dir = GoogleDrive.getFile(path);
        String query = "parents=" + "'" + dir.getId() + "'";
        FileList list = GoogleDrive.service.files().list().setQ(query).setFields("nextPageToken, files(id, name, createdTime, mimeType, modifiedTime, parents, fileExtension)").execute();

        List<File> files = new ArrayList<>();
        for (File file: list.getFiles()) {
            if (!file.getMimeType().equals("application/vnd.google-apps.folder")) {
                files.add(file);
            }
        }

        return getJavaFiles(files);
    }

    @Override
    public boolean containsFiles(String path, List fileNames) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        File dir = GoogleDrive.getFile(path);
        String query = "parents=" + "'" + dir.getId() + "'";
        FileList list = GoogleDrive.service.files().list().setQ(query).setFields("nextPageToken, files(id, name, createdTime, mimeType, modifiedTime, parents, fileExtension)").execute();

        boolean containsAll = false;

        List<File> files = new ArrayList<>();
        for (File file: list.getFiles()) {
            if (!file.getMimeType().equals("application/vnd.google-apps.folder")) {
                files.add(file);
            }
        }

        return getNames(files).containsAll(fileNames);
    }

    @Override
    public String getDir(String fileName) throws Exception {
        File dir = GoogleDrive.getRootFile(fileName);

        return dir.getName();
    }

    @Override
    public List<java.io.File> sortByName(String dirPath) throws Exception {

        // TODO
        return null;
    }

    @Override
    public List<java.io.File> sortByDate(String dirPath) throws Exception {

        // TODO
        return null;
    }

    @Override
    public List<java.io.File> getFiles(String path, Date pocetak, Date kraj) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        File dir = GoogleDrive.getFile(path);
        String query = "parents=" + "'" + dir.getId() + "'";
        FileList list = GoogleDrive.service.files().list().setQ(query).setFields("nextPageToken, files(id, name, createdTime, mimeType, modifiedTime, parents, fileExtension)").execute();

        List<File> files = new ArrayList<>();
        for (File file: list.getFiles()) {
            if (!file.getMimeType().equals("application/vnd.google-apps.folder")) {
                Date d1 = new Date(String.valueOf(file.getCreatedTime()));
                Date d2 = new Date(String.valueOf(file.getModifiedTime()));
                if((d1.compareTo(pocetak) >= 0 && d1.compareTo(kraj) <= 0) ||
                        (d2.compareTo(pocetak) >= 0 && d2.compareTo(kraj) <= 0)) {
                    files.add(file);
                }
            }
        }

        return getJavaFiles(files);
    }

    @Override
    public List<java.io.File> filtrate(String string) throws Exception {
        return null;
    }

    private List<String> getNames(List<File> files) {
        List<String> names = new ArrayList<>();

        for (File file: files) {
            String name = file.getName();
            names.add(name);
        }

        return names;
    }

    private List<java.io.File> getJavaFiles(List<File> files) {
        List<java.io.File> javaFiles = new ArrayList<>();

        for (File file: files) {
            String name = file.getName();

            java.io.File newFile = new java.io.File(name);
            javaFiles.add(newFile);
        }

        return javaFiles;
    }
}
