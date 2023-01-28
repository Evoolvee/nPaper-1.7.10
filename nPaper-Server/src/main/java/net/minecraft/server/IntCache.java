package net.minecraft.server;

import java.util.ArrayDeque;
import java.util.Deque;

public final class IntCache {
    private static int a = 256;
    private static Deque<int[]> b = new ArrayDeque<int[]>();
    private static Deque<int[]>c = new ArrayDeque<int[]>();
    private static Deque<int[]> d = new ArrayDeque<int[]>();
    private static Deque<int[]> e = new ArrayDeque<int[]>();

    public static synchronized int[] a(int i) {
        int[] aint;

        if (i <= 256) {
        	aint = (b.isEmpty() ? new int[256] : b.poll());
        	if (c.size() < org.spigotmc.SpigotConfig.intCacheLimit) c.add(aint);
        	return aint;
        }
        if (i > a) {
            a = i;
            d.clear();
            e.clear();
            aint = new int[a];
            if (e.size() < org.spigotmc.SpigotConfig.intCacheLimit) e.add(aint);
            return aint;
        }
        aint = (d.isEmpty() ? new int[a] : d.poll());
        if (e.size() < org.spigotmc.SpigotConfig.intCacheLimit) e.add(aint);
        return aint;
    }

    public static synchronized void a() {
        if (!d.isEmpty()) {
            d.removeLast();
        }
        if (!b.isEmpty()) {
            b.removeLast();
        }

        d.addAll(e);
        b.addAll(c);
        e.clear();
        c.clear();
    }

    public static synchronized String b() {
        return "cache: " + d.size() + ", tcache: " + b.size() + ", allocated: " + e.size() + ", tallocated: " + c.size();
    }
}