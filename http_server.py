import http.server
import socketserver

PORT = 8000

Handler = http.server.SimpleHTTPRequestHandler
Handler.extensions_map.update({'.mjs': 'application/javascript'})

with socketserver.TCPServer(("", PORT), Handler) as httpd:
    print("starting server at localhost:" + str(PORT))
    httpd.serve_forever()


