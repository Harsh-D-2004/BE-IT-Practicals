import requests
from bs4 import BeautifulSoup
from urllib.parse import urljoin, urlparse
import time


crawled_data = {
    "pages": [],    
    "links": {},    
}

visited = set()

def crawl(url, depth):
    if depth == 0 or url in visited:
        return
    
    visited.add(url)
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3'
    }
        
    try:
        response = requests.get(url, headers=headers)
        if response.status_code != 200:
            print(f"Failed to retrieve {url} (status code: {response.status_code})")
            return

        soup = BeautifulSoup(response.text, 'lxml')
        print(f"Crawling: {url}")
        
        crawled_data["pages"].append(url)
        crawled_data["links"][url] = []  
        
        for link in soup.find_all('a', href=True):
            next_url = urljoin(url, link['href'])
            
            if urlparse(url).netloc == urlparse(next_url).netloc:
                crawled_data["links"][url].append(next_url)
                crawl(next_url, depth - 1)
        
        time.sleep(1)
    
    except Exception as e:
        print(f"Error crawling {url}: {e}")


def print_stats():
    print("\n--- Crawling Stats ---")
    total_pages = len(crawled_data["pages"])
    total_links = sum(len(links) for links in crawled_data["links"].values())
    
    print(f"Total pages crawled: {total_pages}")
    print(f"Total links found: {total_links}")
    
if __name__ == "__main__":
    start_url = input("Enter the URL to crawl: ")
    depth_limit = int(input("Enter the crawling depth limit (e.g., 2): "))


    crawl(start_url, depth_limit)
    print_stats()