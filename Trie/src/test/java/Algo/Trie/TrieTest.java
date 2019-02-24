package Algo.Trie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import com.google.common.collect.Lists;

public class TrieTest {
	
	@Test
	public void testInsertionBasic(){
		Trie trie = new Trie();
		String word = "meawo" ;
		int expectedSize = word.length() ;
		trie.insert(word);
		
		assertEquals(trie.getTrieSize(), expectedSize);
		assertAllSubstringsStartsWithReturnTrue(trie, word) ;
		assertAllSubstringSearchReturnFalse(trie, word) ;
		assertTrue(trie.search("meawo"));
	}	
	
	@Test
	public void testInsertionMultipleNonIntersectingWords() {

		Trie trie = new Trie();

		String word = "hello" ;
		int expectedSize = word.length() ;
		trie.insert(word);
		
		assertEquals(trie.getTrieSize(), expectedSize);
		assertAllSubstringsStartsWithReturnTrue(trie, word) ;
		assertAllSubstringSearchReturnFalse(trie, word) ;
		assertFalse(trie.search(""));
		assertFalse(trie.search("o"));
		
		word = "mellborn" ;
		expectedSize = expectedSize + word.length() ;
		trie.insert(word);
		
		assertEquals(trie.getTrieSize(), expectedSize);
		assertAllSubstringsStartsWithReturnTrue(trie, word) ;
		assertAllSubstringSearchReturnFalse(trie, word) ;

		word = "black" ;
		expectedSize = expectedSize + word.length() ;
		trie.insert(word);
		
		assertEquals(trie.getTrieSize(), expectedSize);
		assertAllSubstringsStartsWithReturnTrue(trie, word) ;
		assertAllSubstringSearchReturnFalse(trie, word) ;

		word = "rat" ;
		expectedSize = expectedSize + word.length() ;
		trie.insert(word);
		
		assertEquals(trie.getTrieSize(), expectedSize);
		assertAllSubstringsStartsWithReturnTrue(trie, word) ;
		assertAllSubstringSearchReturnFalse(trie, word) ;
		
	}
	
	@Test
	public void testInsertionMultipleIntersectingWords() {

		Trie trie = new Trie();
		String word = "hello" ;
		trie.insert(word);
		assertEquals(trie.getTrieSize(), 5);
		assertFalse(trie.search(""));

		assertTrue(trie.search(word));
		assertAllSubstringsStartsWithReturnTrue(trie, word) ;
		assertAllSubstringSearchReturnFalse(trie, word) ;
		assertFalse(trie.search("o"));

		word = "hellborn" ;
		trie.insert(word);
		assertEquals(trie.getTrieSize(), 9);
		
		assertTrue(trie.search(word));
		assertAllSubstringsStartsWithReturnTrue(trie, word) ;
		assertAllSubstringSearchReturnFalse(trie, word) ;

		word = "hat" ;
		trie.insert(word);
		assertEquals(trie.getTrieSize(), 11);
		assertTrue(trie.search(word));
		assertAllSubstringsStartsWithReturnTrue(trie, word) ;
		assertAllSubstringSearchReturnFalse(trie, word) ;

		word = "rat" ;
		trie.insert(word);
		assertEquals(trie.getTrieSize(), 14);
		assertTrue(trie.search(word));
		assertAllSubstringsStartsWithReturnTrue(trie, word) ;
		assertAllSubstringSearchReturnFalse(trie, word) ;

		assertFalse(trie.search("at"));
	}
	
	@Test
	public void testInsertArray_NonIntersectingWords(){

		Trie trie = new Trie();
		String word1 = "hallaloja"; 
		String word2 = "rattatooy";
		String word3 = "baklaweez";
		int expectedSize = word1.length() + word2.length() + word3.length() ;
		String[] array = new String[] { word1, word2, word3} ;
		trie.insertArray(array);
		
		assertEquals(trie.getTrieSize(), expectedSize);
		
		for(String word : array){
			assertAllSubstringsStartsWithReturnTrue(trie, word) ;
			assertAllSubstringSearchReturnFalse(trie, word) ;
			assertTrue(trie.search(word));
		}
	}
	
	@Test
	public void testInsertArray_IntersectingWords(){

		Trie trie = new Trie();
		String word1 = "hallalo"; 
		String word2 = "hallyaya";
		String word3 = "baklaweez";
		String word4 = "baklowa";
		int expectedSize = 23 ;
		String[] array = new String[] { word1, word2, word3, word4} ;
		
		trie.insertArray(array);
		assertEquals(trie.getTrieSize(), expectedSize);
		
		for(String word : array){
			assertAllSubstringsStartsWithReturnTrue(trie, word) ;
			assertAllSubstringSearchReturnFalse(trie, word) ;
			assertTrue(trie.search(word));
		}
	}
	
	@Test
	public void testGetAllWordsStartingWith_MultipleNonIntersectingWords(){

		Trie trie = new Trie();
		String word1 = "hallaloja"; 
		String word2 = "rattatooy";
		String word3 = "baklaweez";
		
		trie.insertArray(new String[] { word1, word2, word3});
		assertEquals(trie.getAllWordsStartingWith("h"), Lists.newArrayList(word1));
		assertEquals(trie.getAllWordsStartingWith("r"), Lists.newArrayList(word2));
		assertEquals(trie.getAllWordsStartingWith("b"), Lists.newArrayList(word3));

		assertEquals(trie.getAllWordsStartingWith("t"), Lists.newArrayList());
		assertEquals(trie.getAllWordsStartingWith("l"), Lists.newArrayList());

		List<String> expectedList = Lists.newArrayList(word1, word2, word3) ;
		Collections.sort(expectedList);		
		List<String> actualList = trie.getAllWordsStartingWith("") ;
		Collections.sort(actualList);
		
		assertEquals(actualList, expectedList);
	}

	@Test
	public void testGetAllWordsStartingWith_MultipleIntersectingWords_Simple(){

		Trie trie = new Trie();
		String word1 = "halaloja";
		String word2 = "halyaa";
		String word3 = "baka";
		String word4 = "baky";
		
		trie.insertArray(new String[] { word1, word2, word3, word4});
		assertAllSubstringsGetAllWordsStartingWith(trie, "hal", Lists.newArrayList(word1, word2)) ;
		assertEquals(trie.getAllWordsStartingWith("hala"), Lists.newArrayList(word1));
		assertEquals(trie.getAllWordsStartingWith("haly"), Lists.newArrayList(word2));

		assertAllSubstringsGetAllWordsStartingWith(trie, "bak", Lists.newArrayList(word3, word4)) ;
		assertEquals(trie.getAllWordsStartingWith("baka"), Lists.newArrayList(word3));
		assertEquals(trie.getAllWordsStartingWith("baky"), Lists.newArrayList(word4));

		assertEquals(trie.getAllWordsStartingWith("t"), Lists.newArrayList());
		assertEquals(trie.getAllWordsStartingWith("l"), Lists.newArrayList());

		List<String> expectedList = Lists.newArrayList(word1, word2, word3, word4) ;
		Collections.sort(expectedList);		
		List<String> actualList = trie.getAllWordsStartingWith("") ;
		Collections.sort(actualList);
		
		assertEquals(actualList, expectedList);
	}

	
	@Test
	public void testGetAllWordsStartingWith_MultipleIntersectingWords_MoreComplex(){

		Trie trie = new Trie();
		int numberOfWordsPerPrefix = 3 ;
		int lengthOfSuffix = 3 ;
		int expectedSize = 0 ;
		
		int prefixLength = 3 ;
		String[] prefixes = new String[]{"aby", "cet", "iot" , "zor"} ;
		String charactersToUse = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" ;
		
		HashMap<String, List<String>> prefixWords = new HashMap<String, List<String>>() ;
		
		for(int i = 0 ; i < prefixes.length ; i++){
			String prefix = prefixes[i] ;
			List<String> prefixGeneratedWords = generateListOfWordsWithPrefix(numberOfWordsPerPrefix, lengthOfSuffix, charactersToUse, prefix) ;
			for(String word : prefixGeneratedWords){
				charactersToUse = excludeCharactersFromString(charactersToUse, word.substring(prefixLength));
			}
			prefixWords.put(prefix, prefixGeneratedWords) ;
			expectedSize = expectedSize + prefixLength + lengthOfSuffix*numberOfWordsPerPrefix ;

			String[] array = new String[prefixGeneratedWords.size()];
			array = prefixGeneratedWords.toArray(array);
			trie.insertArray(array);
		}

		assertEquals(trie.getTrieSize(), expectedSize);
		for(Map.Entry<String, List<String>> entry : prefixWords.entrySet()){
			assertAllSubstringsGetAllWordsStartingWith(trie, entry.getKey(), entry.getValue()) ;
		}
	}
	
	@Test
	public void testPrint_Simple(){
		Trie trie = new Trie();		
		trie.insert("hello");
		String expected = "+--h/\n|  +--e/\n|  |  +--l/\n|  |  |  +--l/\n|  |  |  |  +--o\n" ;
		assertEquals(trie.printTrie(false), expected);
		
		trie.insert("hellborn");
		expected = "+--h/\n|  +--e/\n|  |  +--l/\n|  |  |  +--l/\n|  |  |  |  +--b/\n|  |  |  |  |  +--o/\n|  |  |  |  |  |  +--r/\n|  |  |  |  |  |  |  +--n\n|  |  |  |  +--o\n" ;
		assertEquals(trie.printTrie(false), expected);
	}
	
	private List<String> generateListOfWordsWithPrefix(int listSize, int suffixLength, String characters, String prefix){
		List<String> array = new ArrayList<String>() ;
		for(int i = 0 ; i < listSize ; i++){
			String generatedSuffix = RandomStringUtils.random(suffixLength, characters);
			characters = excludeCharactersFromString(characters, generatedSuffix);
			array.add(prefix + generatedSuffix) ;
		}
		return array ;
	}
	
	private void assertAllSubstringsGetAllWordsStartingWith(Trie trie, String word, List<String> expectedList){
		Collections.sort(expectedList);
		for(int i = 1 ; i < word.length() ; i++){
			List<String> actualList = trie.getAllWordsStartingWith(word.substring(0, i)) ;
			Collections.sort(actualList);
			assertEquals(expectedList, actualList);
		}
	}
	
	private void assertAllSubstringsStartsWithReturnTrue(Trie trie, String word){
		for(int i = 1 ; i < word.length() ; i++){
			assertTrue(trie.startsWith(word.substring(0, i))) ;
		}
	}

	private void assertAllSubstringSearchReturnFalse(Trie trie, String word){
		for(int i = 1 ; i < word.length()-1 ; i++){
			assertFalse(trie.search(word.substring(0, i))) ;
		}
	}
	
	private String excludeCharactersFromString(String characters, String charactersToExclude){
		StringBuffer sb = new StringBuffer() ;
		HashSet<Character> charactersSet = new HashSet<Character>(characters.chars().mapToObj(e -> (char)e).collect(Collectors.toList())) ;
		HashSet<Character> charactersToExcludeSet = new HashSet<Character>(charactersToExclude.chars().mapToObj(e -> (char)e).collect(Collectors.toList())) ;
		charactersSet.removeAll(charactersToExcludeSet);		
		return charactersSet.stream().map(String::valueOf).collect(Collectors.joining());		
	}
}