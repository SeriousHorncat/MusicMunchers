package com.ursarage.musicmunchers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ScaleRepository {
    private static Map<String, List<String>> scales_ = new HashMap<String, List<String>>();;
    private static Map<String, Dificulty> skillLevel_ = new HashMap<String, Dificulty>();

    private static Random random = new Random();
    private static final List<String> cScale = Arrays.asList("C", "D", "E", "F", "G", "A", "B");
    private static final List<String> fScale  = Arrays.asList("F", "G", "A", "Bb", "D", "D", "E");
    private static final List<String> bBScale = Arrays.asList("Bb", "C", "D", "Eb", "F", "G", "A");
    private static final List<String> eBScale = Arrays.asList("Eb", "F", "G", "Ab", "Bb", "C", "D");
    private static final List<String> aBScale = Arrays.asList("Ab", "Bb", "C", "Dd", "Eb", "F", "G");
    private static final List<String> dBScale = Arrays.asList("Db", "Eb", "F", "Gb", "Ab", "Bb", "C");
    private static final List<String> gBScale = Arrays.asList("Gb", "Ab", "Bb", "C", "Db", "Eb", "Fb");
    private static final List<String> bScale = Arrays.asList("B", "C#", "D#", "E", "F#", "G#", "A#");
    private static final List<String> aScale = Arrays.asList("A", "B", "C#", "D", "E", "F#", "B#");
    private static final List<String> eScale = Arrays.asList("E", "F#", "G#", "A", "B", "C#", "D#");
    private static final List<String> dScale = Arrays.asList("D", "E", "F#", "G", "A", "B", "C#");
    private static final List<String> gScale = Arrays.asList("G", "A", "B", "C", "D", "E", "F");

    public enum Dificulty { Easy, Medium, Hard };

    public ScaleRepository()
    {
      scales_.put("C", cScale);
      scales_.put("F", fScale);
      scales_.put("Bb", bBScale);
      scales_.put("Eb", eBScale);
      scales_.put("Ab", aBScale);
      scales_.put("Db", dBScale);
      scales_.put("Gb", gBScale);
      scales_.put("B", bScale);
      scales_.put("A", aScale);
      scales_.put("E", eScale);
      scales_.put("D", dScale);
      scales_.put("G", gScale);
      
      skillLevel_.put("C", Dificulty.Easy);
      skillLevel_.put("F", Dificulty.Easy);
      skillLevel_.put("Bb", Dificulty.Easy);
      skillLevel_.put("Eb", Dificulty.Medium);
      skillLevel_.put("Ab", Dificulty.Medium);
      skillLevel_.put("Db", Dificulty.Hard);
      skillLevel_.put("Gb", Dificulty.Hard);
      skillLevel_.put("B", Dificulty.Hard);
      skillLevel_.put("A", Dificulty.Medium);
      skillLevel_.put("E", Dificulty.Medium);
      skillLevel_.put("D", Dificulty.Easy);
      skillLevel_.put("G", Dificulty.Easy);
    }

    public static List<String> Scale(String scale)
    {
      return scales_.get(scale);
    }
    
    /**
    public static string Scale(Difficulty difficulty)
    {
      skillLevel_.Where(item => item.Value).Select(item => item.Key).ToList();
      string scaleName =
    } **/

    public static boolean doesScaleContains( String scaleName, String note) {
    	List<String> scale = scales_.get(scaleName);
    	return scale.contains(note);
    }
    
    public static String RandomNote()
    {
      String[] scaleNames = scales_.keySet().toArray(new String[0]);
      String scaleName = scaleNames[ random.nextInt(scaleNames.length) ];
      return RandomNote(scaleName);
    }

    public static String RandomNote(String scaleName)
    {
      List<String> scale = scales_.get(scaleName);
	    String note = scale.get( random.nextInt(scale.size()));
      return note;
    }
}
