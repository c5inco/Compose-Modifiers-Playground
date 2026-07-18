import { createReadStream, statSync } from "node:fs";
import { createServer } from "node:http";
import { extname, resolve, sep } from "node:path";
import { fileURLToPath } from "node:url";

const repoRoot = resolve(fileURLToPath(new URL("../", import.meta.url)));
const contentTypes = {
  ".css": "text/css; charset=utf-8",
  ".html": "text/html; charset=utf-8",
  ".js": "text/javascript; charset=utf-8",
  ".mjs": "text/javascript; charset=utf-8",
  ".png": "image/png",
  ".svg": "image/svg+xml",
  ".ttf": "font/ttf",
  ".wasm": "application/wasm",
};

export function startServer(port = 4173) {
  const server = createServer((request, response) => {
    try {
      const pathname = decodeURIComponent(new URL(request.url, "http://localhost").pathname);
      let file = resolve(repoRoot, `.${pathname}`);
      if (file !== repoRoot && !file.startsWith(`${repoRoot}${sep}`)) {
        response.writeHead(403).end();
        return;
      }
      if (statSync(file).isDirectory()) file = resolve(file, "index.html");
      response.writeHead(200, {
        "Content-Type": contentTypes[extname(file)] ?? "application/octet-stream",
        "Cache-Control": "no-store",
      });
      createReadStream(file).pipe(response);
    } catch {
      response.writeHead(404).end();
    }
  });
  return new Promise((resolveReady, reject) => {
    server.once("error", reject);
    server.listen(port, "127.0.0.1", () => resolveReady(server));
  });
}

if (process.argv[1] === fileURLToPath(import.meta.url)) {
  await startServer();
  console.log("Web target server listening on http://127.0.0.1:4173");
}
