package com.iti.mealmate.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeUtils {

    private static final String VIDEO_ID_PATTERN = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%‌​2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

    public static String extractVideoId(String videoUrl) {
        if (videoUrl == null || videoUrl.trim().isEmpty()) {
            return null;
        }
        Pattern compiledPattern = Pattern.compile(VIDEO_ID_PATTERN);
        Matcher matcher = compiledPattern.matcher(videoUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
