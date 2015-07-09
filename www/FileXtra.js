var
  exec = require('cordova/exec');

var
  FileXtra = {
    getFreeDiskSpace: function (successCallback, errorCallback) {

      var
        done = function (result) {
          var bytes = parseInt(result, 10);
          successCallback(bytes);
        };

      exec(done, errorCallback, 'FileXtra', 'getFreeDiskSpace', []);
    },
    getSize: function (path, successCallback, errorCallback) {

      var
        done = function (result) {
          var bytes = parseInt(result, 10);
          successCallback(bytes);
        };

      exec(done, errorCallback, 'FileXtra', 'getSize', [ path ]);
    }
  };

module.exports = FileXtra;
