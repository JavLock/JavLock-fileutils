package com.github.javlock.fileutils;

import java.io.File;
import java.util.Optional;

public class MoveFileToDirByExt {

	public static void main(String[] args) {
		if (args.length != 1) {
			printHelp();
		}
		File dir;
		File tmpFile = new File(args[0]);
		if (tmpFile.isFile()) {
			dir = tmpFile.getParentFile();
		} else {
			dir = tmpFile;
		}

		moveByExt(dir);

	}

	public static Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	private static void moveByExt(File tmp) {
		for (File file : tmp.listFiles()) {
			if (!file.isFile()) {
				continue;
			}
			String pathString = file.getAbsolutePath();
			Optional<String> extOptional = getExtensionByStringHandling(pathString);
			if (extOptional.isPresent()) {
				String ext = extOptional.get();
				File newDir = new File(file.getParentFile(), ext);
				File newFile = new File(newDir, file.getName());
				if (!newFile.getParentFile().exists()) {
					newFile.getParentFile().mkdirs();
				}
				System.out.println("OK " + file.renameTo(newFile) + " " + pathString + " " + ext);
			} else {
				System.out.println("NO " + pathString);
			}

		}
	}

	private static void printHelp() {
		StringBuilder helpBuilder = new StringBuilder();
		helpBuilder.append("Help:").append('\n');
		helpBuilder.append("java -jar THISFILE dir").append('\n');
		helpBuilder.append("OR:").append('\n');
		helpBuilder.append("java -jar THISFILE dir").append(File.separatorChar).append("file.ext").append('\n');
		System.err.println(helpBuilder);
	}

}
