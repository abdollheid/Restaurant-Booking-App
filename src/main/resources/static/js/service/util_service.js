App.factory('Util', [function () {
    //$resource() function returns an object of resource class
    return {
        parseError: function (errorObj) {
            let errorMessage = '';
            let title = 'Error';
            if (errorObj && errorObj.status) {
                title = errorObj.status;

                if (title == 401)
                    title =  "Unauthorized, please log in";


            }

            errorMessage = title + ":\n";
            for (let i = 0; errorObj && errorObj.errors && i < errorObj.errors.length; ++i) {
                let error = 'Error';
                if (errorObj.errors[i].error)
                    error = errorObj.errors[i].error;

                let message = 'Something wrong happened';
                if (errorObj.errors[i].message)
                    message = errorObj.errors[i].message;

                errorMessage += error + ': ' + message + '\n';
            }

            return errorMessage;
        }

    };
}]);