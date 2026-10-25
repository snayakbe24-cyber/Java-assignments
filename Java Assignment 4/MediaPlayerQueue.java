import java.util.*;

/**
 * Problem 2: Media Player Queue Engine
 * LinkedList<Song> + ListIterator for O(1) insert/remove at the cursor,
 * bidirectional navigation, priority queueing, duplicate removal, and history.
 */
public class MediaPlayerQueue {

    // ------------------------------------------------------------------ Song
    static final class Song {
        private final String title;
        private final String artist;

        Song(String title, String artist) {
            this.title = title;
            this.artist = artist;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Song)) return false;
            Song s = (Song) o;
            return title.equalsIgnoreCase(s.title) && artist.equalsIgnoreCase(s.artist);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title.toLowerCase(), artist.toLowerCase());
        }

        @Override
        public String toString() { return title + " - " + artist; }
    }

    // ----------------------------------------------------------------- Queue
    private enum Dir { NONE, FORWARD, BACKWARD }

    private static final int HISTORY_LIMIT = 5;

    private final LinkedList<Song> playlist = new LinkedList<>();
    private final Deque<Song> history = new ArrayDeque<>();   // most recent first
    private ListIterator<Song> cursor = playlist.listIterator();
    private Dir lastDir = Dir.NONE;

    /** Any structural change invalidates the iterator, so restart from the front. */
    private void resetCursor() {
        cursor = playlist.listIterator();
        lastDir = Dir.NONE;
    }

    // ------------------------------------------------------- playlist features
    public void addToEnd(Song s) {
        playlist.addLast(s);
        resetCursor();
    }

    /** Priority queueing: the song will be the first one played. */
    public void addToFront(Song s) {
        playlist.addFirst(s);                 // O(1)
        resetCursor();
    }

    /** Insert at an arbitrary position using the iterator (no repeated get(i) calls). */
    public void insertAt(int index, Song s) {
        if (index < 0 || index > playlist.size()) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        ListIterator<Song> it = playlist.listIterator(index);
        it.add(s);
        resetCursor();
    }

    /** Removes duplicate entries (keeps the first occurrence) in one O(n) pass. */
    public int removeDuplicates() {
        Set<Song> seen = new HashSet<>();
        int removed = 0;
        Iterator<Song> it = playlist.iterator();
        while (it.hasNext()) {
            if (!seen.add(it.next())) {       // add() returns false if already present
                it.remove();                  // O(1) removal on a LinkedList
                removed++;
            }
        }
        resetCursor();
        return removed;
    }

    // ------------------------------------------------------------ navigation
    /** Plays the next song; returns null at the end of the playlist. */
    public Song playNext() {
        if (lastDir == Dir.BACKWARD && cursor.hasNext()) {
            cursor.next();                    // skip the element previous() already returned
        }
        if (!cursor.hasNext()) return null;
        Song s = cursor.next();
        lastDir = Dir.FORWARD;
        recordHistory(s);
        return s;
    }

    /** Plays the previous song; returns null at the start of the playlist. */
    public Song playPrevious() {
        if (lastDir == Dir.FORWARD && cursor.hasPrevious()) {
            cursor.previous();                // skip the element next() already returned
        }
        if (!cursor.hasPrevious()) return null;
        Song s = cursor.previous();
        lastDir = Dir.BACKWARD;
        recordHistory(s);
        return s;
    }

    private void recordHistory(Song s) {
        history.addFirst(s);
        if (history.size() > HISTORY_LIMIT) history.removeLast();
    }

    // --------------------------------------------------------------- display
    public void printPlaylist() {
        System.out.println("Playlist: " + playlist);
    }

    public void printHistory() {
        System.out.println("Recently played (newest first): " + history);
    }

    /** Forward and backward traversal of the whole list using ListIterator. */
    public void printBothDirections() {
        ListIterator<Song> it = playlist.listIterator();
        System.out.println("Forward:");
        while (it.hasNext()) System.out.println("  " + it.next());
        System.out.println("Backward:");
        while (it.hasPrevious()) System.out.println("  " + it.previous());
    }

    // ------------------------------------------------------------------ main
    public static void main(String[] args) {
        MediaPlayerQueue q = new MediaPlayerQueue();

        q.addToEnd(new Song("Song A", "Artist 1"));
        q.addToEnd(new Song("Song B", "Artist 2"));
        q.addToEnd(new Song("Song C", "Artist 3"));
        q.addToEnd(new Song("Song B", "Artist 2"));      // duplicate
        q.addToEnd(new Song("Song A", "Artist 1"));      // duplicate
        q.printPlaylist();

        q.addToFront(new Song("Priority Hit", "Artist 9"));
        q.insertAt(2, new Song("Inserted", "Artist 5"));
        q.printPlaylist();

        System.out.println("Duplicates removed: " + q.removeDuplicates());
        q.printPlaylist();

        System.out.println("\n-- Navigation --");
        System.out.println("Next:     " + q.playNext());
        System.out.println("Next:     " + q.playNext());
        System.out.println("Next:     " + q.playNext());
        System.out.println("Previous: " + q.playPrevious());
        System.out.println("Previous: " + q.playPrevious());
        System.out.println("Next:     " + q.playNext());
        q.printHistory();

        System.out.println();
        q.printBothDirections();
    }
}
