<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>URL Details</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1>URL Details</h1>
    <dl class="row">
        <dt class="col-sm-3">ID</dt>
        <dd class="col-sm-9">${url.id}</dd>

        <dt class="col-sm-3">Name</dt>
        <dd class="col-sm-9">${url.name}</dd>

        <dt class="col-sm-3">Created At</dt>
        <dd class="col-sm-9">${url.createdAt}</dd>
    </dl>
</div>
</body>
</html>
