<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>URL Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1 class="mt-5">URL Details</h1>
    <div class="mt-4">
        <h2>${url.getName()}</h2>
        <p>ID: ${url.getId()}</p>
        <p>Дата добавления: ${url.getCreatedAt()}</p>
    </div>
    <a href="/urls" class="btn btn-secondary mt-3">Back to URL list</a>
</div>
</body>
</html>
