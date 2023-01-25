package net.minecraft.server;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Queue;

final class IntCache {
    private static int a = 256;
    private static final Queue<int[]> b = new LinkedList<int[]>();
    private static final Queue<int[]> c = new LinkedList<int[]>();
    private static final Queue<int[]> d = new LinkedList<int[]>();
    private static final Queue<int[]> e = new LinkedList<int[]>();
    
    public static int[] a(int i) {
        int[] aint;
        if (i <= 256) {
            aint = b.isEmpty() ? new int[256] : b.poll();
            if (c.size() < org.spigotmc.SpigotConfig.intCacheLimit) c.add(aint);
            return aint;
        }
        if (i > a) {
            a = i;
            d.clear();
            e.clear();
            aint = (int[]) Array.newInstance(int.class, a);
            e.add(aint);
            return aint;
        }
        aint = d.poll();
        if (e.size() < org.spigotmc.SpigotConfig.intCacheLimit) e.add(aint);
        return aint;
    }

    public static void a() {
        d.offer(e.poll());
        b.offer(c.poll());
        e.clear();
        c.clear();
    }

    public static String b() {
        return "cache: " + d.size() + ", tcache: " + b.size() + ", allocated: " + e.size() + ", tallocated: " + c.size();
    }
}
