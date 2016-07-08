#!/usr/bin/python

import sys

print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)

with open('/Users/mduby/Apps/WorkspaceIntelliJ/dig-diabetes-portal/RelianceDemo/testOut.txt', 'w') as f:
    f.write("%s\n" % str(len(sys.argv)))
    f.write("%s\n" % str(sys.argv))

f.close()
