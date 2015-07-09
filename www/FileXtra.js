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
    getSize: function (url, successCallback, errorCallback) {

      var
        done = function (result) {
          var bytes = parseInt(result, 10);
          successCallback(bytes);
        };

      exec(done, errorCallback, 'FileXtra', 'getSize', [ url ]);
    }
  };

module.exports = FileXtra;
