angular.module('d3', [])
    .factory('d3Service', ['$document', '$window', '$q', '$rootScope',
        function($document, $window, $q, $rootScope) {
            var d = $q.defer(),
                d3service = {
                    d3: function() { return d.promise; }
                };
            function onScriptLoad() {
                // Load client in the browser
                $rootScope.$apply(function() { d.resolve($window.d3); });
            }
            var scriptTag = $document[0].createElement('script');
            scriptTag.type = 'text/javascript';
            scriptTag.async = true;
            scriptTag.src = 'http://d3js.org/d3.v3.min.js';
            scriptTag.onreadystatechange = function () {
                if (this.readyState == 'complete') onScriptLoad();
            }
            scriptTag.onload = onScriptLoad;

            var s = $document[0].getElementsByTagName('body')[0];
            s.appendChild(scriptTag);

            scriptTag = $document[0].createElement('script');
            scriptTag.type = 'text/javascript';
            scriptTag.async = true;
            scriptTag.src = 'https://cdn.datatables.net/1.10.8/js/jquery.dataTables.min.js';
            scriptTag.onreadystatechange = function () {
                if (this.readyState == 'complete') onScriptLoad();
            }
            scriptTag.onload = onScriptLoad;

            s = $document[0].getElementsByTagName('body')[0];
            s.appendChild(scriptTag);

            scriptTag = $document[0].createElement('link');
            scriptTag.rel = 'stylesheet';
            scriptTag.href = 'http://cdn.datatables.net/1.10.7/css/jquery.dataTables.css';
            scriptTag.onreadystatechange = function () {
                if (this.readyState == 'complete') onScriptLoad();
            }
            scriptTag.onload = onScriptLoad;

            s = $document[0].getElementsByTagName('body')[0];
            s.appendChild(scriptTag);

            return d3service;
        }]);