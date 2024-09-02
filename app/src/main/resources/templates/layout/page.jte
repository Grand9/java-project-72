<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Анализатор страниц</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
<nav class="navbar navbar-dark bg-dark navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
    <div class="container-fluid text-white">
        <a class="navbar-brand" href="/">Анализатор страниц</a>
    </div>
</nav>

<main class="flex-grow-1">
    <div class="container-lg mt-5">
        <h1>Добавьте URL</h1>
        <form method="post" action="/urls">
            <div class="mb-3">
                <label for="url" class="form-label">URL</label>
                <input type="text" class="form-control" id="url" name="url" required>
            </div>
            <button type="submit" class="btn btn-primary">Добавить</button>
        </form>
    </div>
</main>

<footer class="footer border-top py-3 mt-5 bg-light">
    <div class="text-center">
        created by
        <a href="https://ru.hexlet.io/" target="_blank">Hexlet</a>
    </div>
</footer>

</body>
</html>
