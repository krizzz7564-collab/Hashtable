import java.util.*;

class DNSEntry {
    String ipAddress;
    long expiryTime;

    DNSEntry(String ipAddress, long ttlSeconds) {
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class DNSCache {

    private HashMap<String, DNSEntry> cache = new HashMap<>();

    // resolve domain
    public String resolve(String domain) {

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                return "Cache HIT → " + entry.ipAddress;
            } else {
                cache.remove(domain);
                return "Cache EXPIRED";
            }
        }

        return "Cache MISS";
    }

    // add entry
    public void addEntry(String domain, String ip, int ttl) {
        cache.put(domain, new DNSEntry(ip, ttl));
    }

    public static void main(String[] args) throws InterruptedException {

        DNSCache dns = new DNSCache();

        dns.addEntry("google.com", "172.217.14.206", 5);

        System.out.println(dns.resolve("google.com"));

        Thread.sleep(6000); // wait for expiry

        System.out.println(dns.resolve("google.com"));
    }
}