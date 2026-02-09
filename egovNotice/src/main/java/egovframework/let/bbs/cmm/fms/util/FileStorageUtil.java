package egovframework.let.bbs.cmm.fms.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileStorageUtil {
	private final String baseDir;

	public FileStorageUtil(String baseDir) {
		if (baseDir == null || baseDir.isBlank()) {
			throw new IllegalArgumentException("baseDir is required");
		}
		this.baseDir = baseDir;
	}

	/** YYYY/MM/DD 하위 폴더에 저장 */
	public StoredFile store(MultipartFile mf) throws IOException {
		if (mf == null || mf.isEmpty()) {
			throw new IllegalArgumentException("empty file");
		}

		String original = safeFilename(mf.getOriginalFilename());
		String ext = extractExt(original);

		String subDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		File dir = new File(baseDir, subDir);
		if (!dir.exists() && !dir.mkdirs()) {
			throw new IOException("failed to create dir: " + dir.getAbsolutePath());
		}

		String storedName = UUID.randomUUID().toString().replace("-", "");
		if (!ext.isEmpty()) {
			storedName += "." + ext;
		}

		File dest = new File(dir, storedName);
		mf.transferTo(dest);

		return new StoredFile(original, storedName, ext, dest.length(), dir.getAbsolutePath());
	}

	private String safeFilename(String name) {
		if (name == null) {
			return "file";
		}
		return name.replaceAll("[\\\\/:*?\"<>|\\r\\n\\t]", "_");
	}

	private String extractExt(String filename) {
		int idx = filename.lastIndexOf('.');
		if (idx < 0 || idx == filename.length() - 1) {
			return "";
		}
		return filename.substring(idx + 1).toLowerCase();
	}

	public static class StoredFile {
		public final String originalName;
		public final String storedName;
		public final String ext;
		public final long size;
		public final String storedPath; // FILE_STRE_COURS

		public StoredFile(String originalName, String storedName, String ext, long size, String storedPath) {
			this.originalName = originalName;
			this.storedName = storedName;
			this.ext = ext;
			this.size = size;
			this.storedPath = storedPath;
		}
	}
}
