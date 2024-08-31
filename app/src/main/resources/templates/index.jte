<!DOCTYPE html>
<html>
<head>
    <title>URL Submission</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    @if (flashMessage != null && !flashMessage.isEmpty())
        <div class="alert alert-${flashType}" role="alert">
            ${flashMessage}
        </div>
    @endif

    <h1>Submit a URL</h1>
    <form action="/urls" method="post">
        <div class="form-group">
            <label for="url">URL</label>
            <input type="text" id="url" name="url" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>
