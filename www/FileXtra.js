var
  exec = require('cordova/exec');

var
  FileXtra = {
    getFreeDiskSpace: function (successCallback, errorCallback) {
      exec(successCallback, errorCallback, 'FileXtra', 'getFreeDiskSpace', []);
    }
  };

module.exports = FileXtra;
