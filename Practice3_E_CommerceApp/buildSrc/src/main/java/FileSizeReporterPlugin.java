import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FileSizeReporterPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register("reportSizes", FileSizeReporterTask.class, task -> {
            task.setDescription("Reports project size and largest files");
            task.setGroup("reporting");
        });
    }

    public static class FileSizeReporterTask extends DefaultTask {

        @TaskAction
        public void reportSizes() {
            File projectDir = getProject().getProjectDir();

            List<FileInfo> allFiles = new ArrayList<>();
            long totalSize = collectFiles(projectDir, allFiles);

            List<FileInfo> largestFiles = allFiles.stream()
                    .sorted(Comparator.comparingLong(FileInfo::getSize).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            Map<String, Integer> fileCountByExtension = new HashMap<>();
            Map<String, Long> sizeByExtension = new HashMap<>();

            for (FileInfo fileInfo : allFiles) {
                String ext = getFileExtension(fileInfo.getName()).toUpperCase();
                fileCountByExtension.merge(ext, 1, Integer::sum);
                sizeByExtension.merge(ext, fileInfo.getSize(), Long::sum);
            }

            printReport(totalSize, allFiles.size(), largestFiles, fileCountByExtension, sizeByExtension);
        }

        private long collectFiles(File directory, List<FileInfo> allFiles) {
            long totalSize = 0;

            File[] files = directory.listFiles();
            if (files == null) return 0;

            for (File file : files) {

                if (file.isDirectory()) {
                    String dirName = file.getName();
                    if (!shouldSkipDirectory(dirName)) {
                        totalSize += collectFiles(file, allFiles);
                    }
                } else {
                    long fileSize = file.length();
                    totalSize += fileSize;
                    allFiles.add(new FileInfo(file.getName(), fileSize, getRelativePath(file)));
                }
            }

            return totalSize;
        }

        private boolean shouldSkipDirectory(String dirName) {
            return dirName.equals(".git") ||
                    dirName.equals(".gradle") ||
                    dirName.equals("build") ||
                    dirName.equals("node_modules") ||
                    dirName.equals(".idea") ||
                    dirName.equals("target");
        }

        private String getRelativePath(File file) {
            File projectDir = getProject().getProjectDir();
            return projectDir.toPath().relativize(file.toPath()).toString();
        }

        private String getFileExtension(String fileName) {
            int lastDot = fileName.lastIndexOf('.');
            return lastDot > 0 ? fileName.substring(lastDot + 1) : "NO_EXT";
        }

        private void printReport(long totalSize, int totalFiles, List<FileInfo> largestFiles,
                                 Map<String, Integer> fileCountByExtension, Map<String, Long> sizeByExtension) {

            System.out.println("\n" + "=".repeat(60));
            System.out.println("           FILE SIZE REPORT");
            System.out.println("=".repeat(60));


            System.out.printf("Total Project Size: %s%n", formatSize(totalSize));
            System.out.printf("Total Files: %,d%n", totalFiles);
            System.out.printf("Average File Size: %s%n", formatSize(totalSize / Math.max(totalFiles, 1)));

            System.out.println("\n" + "-".repeat(60));
            System.out.println("TOP 5 LARGEST FILES:");
            System.out.println("-".repeat(60));

            for (int i = 0; i < largestFiles.size(); i++) {
                FileInfo file = largestFiles.get(i);
                System.out.printf("%d. %-40s %s%n",
                        i + 1,
                        truncateFileName(file.getPath(), 40),
                        formatSize(file.getSize())
                );
            }

            System.out.println("\n" + "-".repeat(60));
            System.out.println("FILES BY TYPE:");
            System.out.println("-".repeat(60));


            sizeByExtension.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(10)
                    .forEach(entry -> {
                        String ext = entry.getKey();
                        long size = entry.getValue();
                        int count = fileCountByExtension.get(ext);
                        double percentage = (double) size / totalSize * 100;

                        System.out.printf("%-8s: %s (%,d files) - %.1f%%%n",
                                ext, formatSize(size), count, percentage);
                    });

            System.out.println("=".repeat(60) + "\n");
        }

        private String formatSize(long bytes) {
            if (bytes < 1024) return bytes + " B";
            else if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
            else if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024));
            else return String.format("%.1f GB", bytes / (1024.0 * 1024 * 1024));
        }

        private String truncateFileName(String fileName, int maxLength) {
            if (fileName.length() <= maxLength) return fileName;
            return "..." + fileName.substring(fileName.length() - maxLength + 3);
        }
    }

    public static class FileInfo {
        private final String name;
        private final long size;
        private final String path;

        public FileInfo(String name, long size, String path) {
            this.name = name;
            this.size = size;
            this.path = path;
        }

        public String getName() { return name; }
        public long getSize() { return size; }
        public String getPath() { return path; }
    }
}