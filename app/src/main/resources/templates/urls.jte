<!DOCTYPE html>
<html>
<head>
    <title>URL List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>URL List</h1>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>URL</th>
            <th>Created At</th>
        </tr>
        </thead>
        <tbody>
        @for (Url url : urls)
            <tr>
                <td>${url.getId()}</td>
                <td>${url.getName()}</td>
                <td>${url.getCreatedAt()}</td>
            </tr>
        @endfor
        </tbody>
    </table>
</div>
</body>
</html>
